package nanshen.service.impl;

import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
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
    private OssFormalApi ossFormalApi;

    private Map<PublicationStatus, Long> statusCntMap = new HashMap<PublicationStatus, Long>();
    private Map<PublicationStatus, Long> newStatusCntMap = new HashMap<PublicationStatus, Long>();
    private List<SkuInfo> offlineSkuInfoList = new ArrayList<SkuInfo>();
    private List<SkuInfo> onlineSkuInfoList = new ArrayList<SkuInfo>();

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
        offlineSkuInfoList = skuInfoDao.getAll(PublicationStatus.OFFLINE, new PageInfo(1, SystemConstants.DEFAULT_CACHED_SKU_SIZE));
        onlineSkuInfoList = skuInfoDao.getAll(PublicationStatus.ONLINE, new PageInfo(1, SystemConstants.DEFAULT_CACHED_SKU_SIZE));
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
    public boolean update(SkuInfo skuInfo) {
        return skuInfoDao.update(skuInfo);
    }

    @Override
    public ExecInfo update(long skuId, String title, String subTitle, String url, SkuDetailType category, String desc, long operatorId) {
        SkuInfo skuInfo = skuInfoDao.get(skuId);
        skuInfo.setStatus(PublicationStatus.OFFLINE);
        skuInfo.setDetailType(category);
        skuInfo.setTitle(title);
        skuInfo.setSubTitle(subTitle);
        skuInfo.setUrl(url);
        skuInfo.setDescription(desc);
        skuInfo.setUploadUserId(operatorId);
        skuInfo.setCreateTime(new Date());
        if (update(skuInfo)) {
            update();
            return ExecInfo.succ();
        }
        return ExecInfo.fail("更新失败");
    }

    @Override
    public boolean remove(long skuId) {
        updateSkuInfoList();
        return skuInfoDao.remove(skuId);
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
    public SkuInfo get(long skuId) {
        return skuInfoDao.get(skuId);
    }

    @Override
    public SkuInfo getOrCreateSkuInfo(long skuId, long operatorId) {
        if (skuId == 0) {
            return skuInfoDao.insert(new SkuInfo(operatorId));
        } else {
            return skuInfoDao.get(skuId);
        }
    }

    @Override
    public ExecResult<SkuInfo> uploadImage(long skuId, long operatorId, MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        SkuInfo skuInfo = getOrCreateSkuInfo(skuId, operatorId);
        ExecInfo execInfo = uploadImageToOss(file, is, skuInfo);

        if (execInfo.isSucc()) {
            skuInfo.setImgCount(skuInfo.getImgCount() + 1);
            update(skuInfo);
            return ExecResult.succ(skuInfo);
        }
        return ExecResult.fail(execInfo.getMsg());
    }

    @Override
    public List<SkuInfo> getAll(PublicationStatus status, PageInfo pageInfo) {
        if (pageInfo != null && pageInfo.getPage() <= 1) {
            if (status == PublicationStatus.ONLINE) {
                return onlineSkuInfoList;
            } else {
                return offlineSkuInfoList;
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
    public List<SkuInfo> getByLookId(long lookId) {
        return skuInfoDao.getByLookId(lookId);
    }

    @Override
    public boolean changeStatus(long skuId, PublicationStatus publicationStatus) {
        SkuInfo skuInfo = skuInfoDao.get(skuId);
        skuInfo.setStatus(publicationStatus);
        if (update(skuInfo)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public ExecInfo addRelatedSku(long lookId, String skuIdList) {
        String[] skuIdArray = StringUtils.getStringListFromString(skuIdList, ",");
        List<SkuInfo> resultSkuInfoList = new ArrayList<SkuInfo>();

        for (String skuId : skuIdArray) {
            Long skuIdLong = Long.parseLong(skuId);
            SkuInfo skuInfo = get(skuIdLong);
            if (skuInfo == null) {
                return ExecInfo.fail("抱歉，根据所提供的ID: " + skuId + "，未找到该单品");
            }
            resultSkuInfoList.add(skuInfo);
        }

        clearSkuLookInfo(lookId);
        updateSkuLookInfo(lookId, resultSkuInfoList);
        return ExecInfo.succ();
    }

    private void updateSkuLookInfo(long lookId, List<SkuInfo> resultSkuInfoList) {
        for (SkuInfo skuInfo : resultSkuInfoList) {
            if (skuInfo.getLookId() == 0 || skuInfo.getLookId() == lookId) {
                skuInfo.setLookId(lookId);
                update(skuInfo);
            }
        }
    }

    private void clearSkuLookInfo(long lookId) {
        List<SkuInfo> skuInfoList = getByLookId(lookId);
        for (SkuInfo skuInfo : skuInfoList) {
            skuInfo.setLookId(0);
            update(skuInfo);
        }
    }

    private ExecInfo uploadImageToOss(MultipartFile file, InputStream is, SkuInfo skuInfo) {
        String imgKey = getImgKey(file, skuInfo);
        ExecInfo execInfo = ExecInfo.fail("上传云服务失败");
        try {
            execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        } catch (FileNotFoundException e) {
            System.out.println("上传图片文件未找到");
        }
        return execInfo;
    }

    private String getImgKey(MultipartFile file, SkuInfo skuInfo) {
        String imgKey = "images/sku/" + skuInfo.getId() + "/" + skuInfo.getImgCount();
        System.out.println("imgKey : " + imgKey);
        return imgKey;
    }
}
