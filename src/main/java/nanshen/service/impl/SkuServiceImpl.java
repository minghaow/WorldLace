package nanshen.service.impl;

import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
import nanshen.dao.SkuDetailDao;
import nanshen.dao.SkuInfoDao;
import nanshen.data.*;
import nanshen.service.SkuService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.service.common.ScheduledService;
import nanshen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Sku related service
 *
 * @Author WANG Minghao
 */
@Service
public class SkuServiceImpl extends ScheduledService implements SkuService {

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private SkuDetailDao skuDetailDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    private Map<PublicationStatus, Long> statusCntMap = new HashMap<PublicationStatus, Long>();
    private Map<PublicationStatus, Long> newStatusCntMap = new HashMap<PublicationStatus, Long>();
    private List<SkuItem> offlineSkuItemList = new ArrayList<SkuItem>();
    private List<SkuItem> onlineSkuItemList = new ArrayList<SkuItem>();

    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        Date startDate = new Date(startTime - 7 * TimeConstants.DAY_IN_MILLISECONDS);
        updateStatusCntMap(startDate);
        updateSkuInfoList();

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("[SkuService] Update in " + totalTime + "ms");
    }

    private void updateSkuInfoList() {
        offlineSkuItemList = skuInfoDao.getAll(PublicationStatus.OFFLINE, new PageInfo(1, SystemConstants.DEFAULT_CACHED_SKU_SIZE));
        onlineSkuItemList = skuInfoDao.getAll(PublicationStatus.ONLINE, new PageInfo(1, SystemConstants.DEFAULT_CACHED_SKU_SIZE));
    }

    private void updateStatusCntMap(Date startDate) {
        statusCntMap.put(PublicationStatus.ONLINE, skuInfoDao.getCnt(PublicationStatus.ONLINE));
        statusCntMap.put(PublicationStatus.OFFLINE, skuInfoDao.getCnt(PublicationStatus.OFFLINE));
        newStatusCntMap.put(PublicationStatus.ONLINE, skuInfoDao.getCnt(PublicationStatus.ONLINE, startDate));
        newStatusCntMap.put(PublicationStatus.OFFLINE, skuInfoDao.getCnt(PublicationStatus.OFFLINE, startDate));
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public boolean update(SkuItem skuItem) {
        return skuInfoDao.update(skuItem);
    }

    @Override
    public ExecInfo update(long itemId, String title, String subTitle, String url, SkuDetailType category, String desc, long operatorId) {
        SkuItem skuItem = skuInfoDao.get(itemId);
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
    public boolean remove(long itemId) {
        updateSkuInfoList();
        return skuInfoDao.remove(itemId);
    }

    @Override
    public ExecInfo remove(long skuId, AdminUserInfo adminUserInfo) {
        boolean isSucc = skuInfoDao.remove(skuId, adminUserInfo.getId());
        if (isSucc) {
            updateSkuInfoList();
            return ExecInfo.succ();
        }
        return ExecInfo.fail("非上传人，禁止删除");
    }

    @Override
    public SkuItem getSkuItemInfo(long itemId) {
        List<SkuDetail> skuDetailList = getSkuDetailByItemId(itemId);
        SkuItem skuItem = skuInfoDao.get(itemId);
        if (skuItem != null) {
            skuItem.setSkuDetailList(skuDetailList);
        }
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
            return skuInfoDao.get(itemId);
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
        return skuInfoDao.getAll(status, pageInfo);
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
        return skuInfoDao.getByLookId(lookId);
    }

    @Override
    public boolean changeStatus(long itemId, PublicationStatus publicationStatus) {
        SkuItem skuItem = skuInfoDao.get(itemId);
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
