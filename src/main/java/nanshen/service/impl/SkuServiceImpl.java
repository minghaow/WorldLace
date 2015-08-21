package nanshen.service.impl;

import nanshen.constant.SystemConstants;
import nanshen.dao.SkuInfoDao;
import nanshen.data.*;
import nanshen.service.SkuService;
import nanshen.service.api.oss.OssFormalApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Sku related service
 *
 * @Author WANG Minghao
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    @Override
    public boolean update(SkuInfo skuInfo) {
        return skuInfoDao.update(skuInfo);
    }

    @Override
    public boolean update(long skuId, String title, String subTitle, String url, String desc, long operatorId) {
        SkuInfo skuInfo = skuInfoDao.get(skuId);
        skuInfo.setTitle(title);
        skuInfo.setSubTitle(subTitle);
        skuInfo.setUrl(url);
        skuInfo.setDescription(desc);
        skuInfo.setUploadUserId(operatorId);
        skuInfo.setCreateTime(new Date());
        return update(skuInfo);
    }

    @Override
    public boolean remove(long skuId) {
        return skuInfoDao.remove(skuId);
    }

    @Override
    public ExecInfo remove(long skuId, AdminUserInfo adminUserInfo) {
        boolean isSucc = skuInfoDao.remove(skuId, adminUserInfo.getId());
        if (isSucc) {
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
        return skuInfoDao.getAll();
    }

    @Override
    public List<LookTag> getAllTag() {
        return null;
    }

    @Override
    public long getCnt(PublicationStatus status) {
        return 0;
    }

    @Override
    public long getThisWeekCnt(PublicationStatus status) {
        return 0;
    }

    @Override
    public List<SkuInfo> getByLookId(long lookId) {
        return skuInfoDao.getByLookId(lookId);
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
