package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
import nanshen.dao.SalesInfoDao;
import nanshen.dao.SkuDetailDao;
import nanshen.dao.SkuItemDao;
import nanshen.dao.SkuItemDescriptionDao;
import nanshen.data.AdminUserInfo;
import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.PublicationStatus;
import nanshen.data.Sku.*;
import nanshen.data.StyleTag;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.service.CustomerReviewService;
import nanshen.service.SkuService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.service.common.ScheduledService;
import nanshen.utils.LogUtils;
import nanshen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Sku related service
 *
 * @Author WANG Minghao
 */
@Service
public class SkuServiceImpl extends ScheduledService implements SkuService {

    @Autowired
    private SkuItemDao skuItemDao;

    @Autowired
    private SkuDetailDao skuDetailDao;

    @Autowired
    private SalesInfoDao salesInfoDao;

    @Autowired
    private SkuItemDescriptionDao skuItemDescriptionDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    @Autowired
    private CustomerReviewService customerReviewService;

    private Map<PublicationStatus, Long> statusCntMap = new HashMap<PublicationStatus, Long>();
    private Map<PublicationStatus, Long> newStatusCntMap = new HashMap<PublicationStatus, Long>();
    private List<SkuItem> offlineSkuItemList = new ArrayList<SkuItem>();
    private List<SkuItem> onlineSkuItemList = new ArrayList<SkuItem>();

    /** skuId到skuItem信息的缓存 */
    private final LoadingCache<Long, SkuItem> skuItemCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, SkuItem>() {
                        @Override
                        public SkuItem load(Long skuItemId) throws Exception {
                            List<SkuDetail> skuDetailList = getSkuDetailByItemId(skuItemId);
                            List<CustomerReview> customerReviewList = customerReviewService.getByItemId(skuItemId, new PageInfo(1));
                            List<SalesInfo> salesInfoList = salesInfoDao.getBySkuItemId(skuItemId);
                            SkuItemDescription skuItemDescription = skuItemDescriptionDao.get(skuItemId);
                            SkuItem skuItem = skuItemDao.get(skuItemId);
                            if (skuItem != null) {
                                skuItem.setSkuDetailList(skuDetailList);
                                skuItem.setCustomerReviewList(customerReviewList);
                                long totalSalesInfo = 0;
                                long monthlySalesInfo = 0;
                                for (SalesInfo salesInfo : salesInfoList) {
                                    totalSalesInfo += salesInfo.getTotalAmount();
                                    monthlySalesInfo += salesInfo.getMonthlyAmount();
                                }
                                skuItem.setTotalSalesInfo(totalSalesInfo);
                                skuItem.setMonthlySalesInfo(monthlySalesInfo);
                                skuItem.setSkuItemDescription(skuItemDescription);
                                if (org.apache.commons.lang.StringUtils.isNotBlank(skuItem.getRelateItem())) {
                                    List<SkuItem> skuItemList = new ArrayList<SkuItem>();
                                    String[] relateSkuItemIdList = skuItem.getRelateItem().split(",");
                                    for (String skuItemIdString : relateSkuItemIdList) {
                                        SkuItem skuItem1 = skuItemDao.get(Long.parseLong(skuItemIdString));
                                        skuItemList.add(skuItem1);
                                    }
                                    skuItem.setRelateSkuItemList(skuItemList);
                                }
                            }
                            return skuItem;
                        }
                    });


    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        updateSkuInfoList();

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("[SkuService] Update in " + totalTime + "ms");
    }

    private void updateSkuInfoList() {
        offlineSkuItemList = skuItemDao.getAll(PublicationStatus.OFFLINE, new PageInfo(1, SystemConstants.DEFAULT_CACHED_SKU_SIZE));
        onlineSkuItemList = skuItemDao.getAll(PublicationStatus.ONLINE, new PageInfo(1, SystemConstants.DEFAULT_CACHED_SKU_SIZE));
    }

    private void updateStatusCntMap(Date startDate) {
        statusCntMap.put(PublicationStatus.ONLINE, skuItemDao.getCnt(PublicationStatus.ONLINE));
        statusCntMap.put(PublicationStatus.OFFLINE, skuItemDao.getCnt(PublicationStatus.OFFLINE));
        newStatusCntMap.put(PublicationStatus.ONLINE, skuItemDao.getCnt(PublicationStatus.ONLINE, startDate));
        newStatusCntMap.put(PublicationStatus.OFFLINE, skuItemDao.getCnt(PublicationStatus.OFFLINE, startDate));
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public boolean update(SkuItem skuItem) {
        return skuItemDao.update(skuItem);
    }

    @Override
    public ExecInfo update(long itemId, String title, String subTitle, String url, SkuDetailType category, String desc, long operatorId) {
        SkuItem skuItem = skuItemDao.get(itemId);
        skuItem.setStatus(PublicationStatus.OFFLINE);
        skuItem.setTitle(title);
        skuItem.setSubTitle(subTitle);
        skuItem.setUrl(url);
        skuItem.setDescription(desc);
        skuItem.setUploadUserId(operatorId);
        skuItem.setCreateTime(new Date());
        if (update(skuItem)) {
            update();
            return ExecInfo.succ();
        }
        return ExecInfo.fail("更新失败");
    }

    @Override
    public ExecInfo update(long itemId, String title, String subTitle, long price, boolean hasGif, String description1, String points,
                           String infos, String shipSpeed, String notice, String description2, String packageInfo) {
        SkuItem skuItem = skuItemDao.get(itemId);
        skuItem.setTitle(title);
        skuItem.setSubTitle(subTitle);
        skuItem.setPrice(price);
        skuItem.setDescription(description1);
        skuItem.setHasGif(hasGif);
        SkuItemDescription itemDescription = skuItemDescriptionDao.get(itemId);
        if (update(skuItem)) {
            itemDescription.setPoints(points);
            itemDescription.setInfos(infos);
            itemDescription.setShipSpeed(shipSpeed);
            itemDescription.setNotice(notice);
            itemDescription.setDescription(description2);
            itemDescription.setPackageInfo(packageInfo);
            skuItemDescriptionDao.update(itemDescription);
            update();
            skuItemCache.invalidate(itemId);
            return ExecInfo.succ();
        }
        return ExecInfo.fail("更新失败");
    }

    @Override
    public boolean remove(long itemId) {
        boolean isSucc = skuItemDao.remove(itemId);
        updateSkuInfoList();
        return isSucc;
    }

    @Override
    public ExecInfo remove(long skuId, AdminUserInfo adminUserInfo) {
        boolean isSucc = skuItemDao.remove(skuId, adminUserInfo.getId());
        if (isSucc) {
            updateSkuInfoList();
            return ExecInfo.succ();
        }
        return ExecInfo.fail("非上传人，禁止删除");
    }

    @Override
    public SkuItem getSkuItemInfo(long itemId) {
        try {
            return skuItemCache.get(itemId);
        } catch (ExecutionException e) {
            LogUtils.warning("[SkuServiceImpl] Fail to get sku item from skuItemCache!");
        }
        return null;
    }

    @Override
    public SkuItem getOrCreateSkuItemInfo(long itemId) {
        SkuItem skuItem = skuItemDao.get(itemId);
        if (skuItem != null) {
            return getSkuItemInfo(itemId);
        }
        skuItem = skuItemDao.insert(new SkuItem());
        skuItemDescriptionDao.insert(skuItem.getId());
        skuDetailDao.insert(new SkuDetail(skuItem.getId()));
        return skuItem;
    }

    @Override
    public SkuDetail getSkuDetail(long skuId) {
        return skuDetailDao.get(skuId);
    }

    @Override
    public List<SkuDetail> getSkuDetailByItemId(long itemId) {
        return skuDetailDao.getByItemId(itemId);
    }

    @Override
    public SkuItem getOrCreateSkuInfo(long itemId, long operatorId) {
//        if (skuId == 0) {
//            return skuInfoDao.insert(new SkuInfo(operatorId));
//        } else {
            return skuItemDao.get(itemId);
//        }
    }

    @Override
    public ExecResult<SkuItem> uploadImage(long itemId, long operatorId, MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        SkuItem skuItem = getOrCreateSkuInfo(itemId, operatorId);
        ExecInfo execInfo = uploadImageToOss(file, is, skuItem);

        if (execInfo.isSucc()) {
            skuItem.setImgCount(skuItem.getImgCount() + 1);
            update(skuItem);
            return ExecResult.succ(skuItem);
        }
        return ExecResult.fail(execInfo.getMsg());
    }

    @Override
    public List<SkuItem> getAll(PublicationStatus status, PageInfo pageInfo) {
        if (pageInfo != null && pageInfo.getPage() <= 1) {
            if (status == PublicationStatus.ONLINE) {
                return onlineSkuItemList;
            } else {
                return offlineSkuItemList;
            }
        }
        return skuItemDao.getAll(status, pageInfo);
    }

    @Override
    public List<SkuItem> getAll(PageInfo pageInfo) {
        return skuItemDao.getAll(pageInfo);
    }

    @Override
    public List<SkuItem> getAll(StoreType storeType, PageInfo pageInfo) {
        return skuItemDao.getAll(storeType, pageInfo);
    }

    @Override
    public List<SkuItem> getAll(StoreType type, SituationType situationType, PageInfo pageInfo) {
        if (type == null || type == StoreType.UNKNOWN) {
            return skuItemDao.getAll(pageInfo);
        }
        if (situationType == null || situationType == SituationType.UNKNOWN) {
            return skuItemDao.getAll(type, pageInfo);
        }
        return skuItemDao.getAll(type, situationType, pageInfo);
    }

    @Override
    public List<StyleTag> getAllTag() {
        return null;
    }

    @Override
    public long getCnt(PublicationStatus status) {
        return statusCntMap.get(status);
    }

    @Override
    public long getThisWeekCnt(PublicationStatus status) {
        return newStatusCntMap.get(status);
    }

    @Override
    public List<SkuItem> getByLookId(long lookId) {
        return skuItemDao.getByLookId(lookId);
    }

    @Override
    public boolean changeStatus(long itemId, PublicationStatus publicationStatus) {
        SkuItem skuItem = skuItemDao.get(itemId);
        skuItem.setStatus(publicationStatus);
        if (update(skuItem)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public ExecInfo addRelatedSku(long lookId, String skuIdList) {
        String[] skuIdArray = StringUtils.getStringListFromString(skuIdList, ",");
        List<SkuItem> resultSkuItemList = new ArrayList<SkuItem>();

        for (String skuId : skuIdArray) {
            Long skuIdLong = Long.parseLong(skuId);
            SkuItem skuItem = getSkuItemInfo(skuIdLong);
            if (skuItem == null) {
                return ExecInfo.fail("抱歉，根据所提供的ID: " + skuId + "，未找到该单品");
            }
            resultSkuItemList.add(skuItem);
        }

        clearSkuLookInfo(lookId);
        updateSkuLookInfo(lookId, resultSkuItemList);
        return ExecInfo.succ();
    }

    @Override
    public List<SkuDetail> getSkuDetailList(List<Long> skuIdList) {
        return skuDetailDao.get(skuIdList);
    }

    private void updateSkuLookInfo(long lookId, List<SkuItem> resultSkuItemList) {
//        for (SkuInfo skuInfo : resultSkuInfoList) {
//            if (skuInfo.getLookId() == 0 || skuInfo.getLookId() == lookId) {
//                skuInfo.setLookId(lookId);
//                update(skuInfo);
//            }
//        }
    }

    private void clearSkuLookInfo(long lookId) {
//        List<SkuInfo> skuInfoList = getByLookId(lookId);
//        for (SkuInfo skuInfo : skuInfoList) {
//            skuInfo.setLookId(0);
//            update(skuInfo);
//        }
    }

    private ExecInfo uploadImageToOss(MultipartFile file, InputStream is, SkuItem skuItem) {
        String imgKey = getImgKey(file, skuItem);
        ExecInfo execInfo = ExecInfo.fail("上传云服务失败");
        try {
            execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        } catch (FileNotFoundException e) {
            System.out.println("上传图片文件未找到");
        }
        return execInfo;
    }

    private String getImgKey(MultipartFile file, SkuItem skuItem) {
        String imgKey = "images/sku/" + skuItem.getId() + "/" + skuItem.getImgCount();
        System.out.println("imgKey : " + imgKey);
        return imgKey;
    }
}
