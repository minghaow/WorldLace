package nanshen.service.impl;

import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
import nanshen.dao.LookInfoDao;
import nanshen.dao.LookTagDao;
import nanshen.data.*;
import nanshen.service.LookService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.service.common.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搭配的相关服务
 *
 * @Author WANG Minghao
 */
@Service
public class LookServiceImpl extends ScheduledService implements LookService {

    @Autowired
    private LookInfoDao lookInfoDao;

    @Autowired
    private LookTagDao lookTagDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    private Map<PublicationStatus, Long> statusCntMap = new HashMap<PublicationStatus, Long>();
    private Map<PublicationStatus, Long> newStatusCntMap = new HashMap<PublicationStatus, Long>();

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        Date startDate = new Date(startTime - 7 * TimeConstants.DAY_IN_MILLISECONDS);
        statusCntMap.put(PublicationStatus.ONLINE, lookInfoDao.getCnt(PublicationStatus.ONLINE));
        statusCntMap.put(PublicationStatus.OFFLINE, lookInfoDao.getCnt(PublicationStatus.OFFLINE));
        newStatusCntMap.put(PublicationStatus.OFFLINE, lookInfoDao.getCnt(PublicationStatus.OFFLINE, startDate));
        newStatusCntMap.put(PublicationStatus.OFFLINE, lookInfoDao.getCnt(PublicationStatus.OFFLINE, startDate));

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("[LookService] Update in " + totalTime + "ms");
    }

    @Override
    public boolean update(LookInfo lookInfo) {
        return updateLookInfo(lookInfo);
    }

    @Override
    @Transactional
    public boolean update(long lookId, String title, String subTitle, String desc, PublicationStatus status,
                          long operatorId) {
        LookInfo lookInfo = lookInfoDao.get(lookId);
        lookInfo.setTitle(title);
        lookInfo.setSubTitle(subTitle);
        lookInfo.setDescription(desc);
        lookInfo.setStatus(status);
        lookInfo.setUploadUserId(operatorId);
        lookInfo.setCreateTime(new Date());
        return updateLookInfo(lookInfo);
    }

    @Override
    public boolean removeLook(long lookId) {
        return lookInfoDao.remove(lookId);
    }

    @Override
    public ExecInfo removeLook(long lookId, AdminUserInfo adminUserInfo) {
        boolean isSucc = lookInfoDao.remove(lookId, adminUserInfo.getId());
        if (isSucc) {
            return ExecInfo.succ();
        }
        return ExecInfo.fail("非上传人，禁止删除");
    }

    @Override
    public boolean changeStatus(long lookId, PublicationStatus status) {
        LookInfo lookInfo = lookInfoDao.get(lookId);
        lookInfo.setStatus(status);
        return updateLookInfo(lookInfo);
    }

    @Override
    public LookInfo get(long lookId) {
        return lookInfoDao.get(lookId);
    }

    private boolean updateLookInfo(LookInfo lookInfo) {
        if (lookInfoDao.update(lookInfo)) {
            update();
            return true;
        }
        return false;
    }

    @Override
    public LookInfo getOrCreateLookInfo(long lookId, long operatorId) {
        if (lookId == 0) {
            return lookInfoDao.insert(new LookInfo(operatorId));
        } else {
            return lookInfoDao.get(lookId);
        }
    }

    @Override
    public ExecResult<LookInfo> uploadImage(long lookId, long operatorId, MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        LookInfo lookInfo = getOrCreateLookInfo(lookId, operatorId);
        ExecInfo execInfo = uploadImageToOss(file, is, lookInfo);

        if (execInfo.isSucc()) {
            lookInfo.setImgCount(lookInfo.getImgCount() + 1);
            update(lookInfo);
            return ExecResult.succ(lookInfo);
        }
        return ExecResult.fail(execInfo.getMsg());
    }

    @Override
    public List<LookInfo> getAll(PublicationStatus status, PageInfo pageInfo) {
        return lookInfoDao.getAll(status, pageInfo);
    }

    @Override
    public List<LookTag> getAllTag() {
        return lookTagDao.getAll();
    }

    @Override
    public long getCnt(PublicationStatus status) {
        return statusCntMap.get(status);
    }

    @Override
    public long getThisWeekCnt(PublicationStatus status) {
        return statusCntMap.get(status);
    }

    private ExecInfo uploadImageToOss(MultipartFile file, InputStream is, LookInfo lookInfo) {
        String imgKey = getImgKey(file, lookInfo);
        ExecInfo execInfo = ExecInfo.fail("上传云服务失败");
        try {
            execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        } catch (FileNotFoundException e) {
            System.out.println("上传图片文件未找到");
        }
        return execInfo;
    }

    private String getImgKey(MultipartFile file, LookInfo lookInfo) {
        String fileName = file.getOriginalFilename();
        String fileType = "";
        int fileTypeIndex = fileName.lastIndexOf(".");
        if (fileTypeIndex > 0) {
            fileType = fileName.substring(fileTypeIndex);
        }
        String imgKey = "images/look/" + lookInfo.getId() + "/" + lookInfo.getImgCount() + fileType;
        System.out.println("imgKey : " + imgKey);
        return imgKey;
    }
}
