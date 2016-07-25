package nanshen.service.impl;

import nanshen.constant.SystemConstants;
import nanshen.dao.CustomerReviewDao;
import nanshen.dao.CustomerReviewDetailDao;
import nanshen.dao.CustomerReviewImgDao;
import nanshen.dao.CustomerReviewSkuDao;
import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.CustomerReview.CustomerReviewDetail;
import nanshen.data.CustomerReview.CustomerReviewImg;
import nanshen.data.CustomerReview.CustomerReviewSku;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;
import nanshen.service.CustomerReviewService;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.utils.CollectionExtractor;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Service
public class CustomerReviewServiceImpl implements CustomerReviewService {

    @Autowired
    private CustomerReviewDao customerReviewDao;

    @Autowired
    private CustomerReviewSkuDao customerReviewSkuDao;

    @Autowired
    private CustomerReviewDetailDao customerReviewDetailDao;

    @Autowired
    private CustomerReviewImgDao customerReviewImgDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    private CollectionExtractor<Long, CustomerReviewSku> reviewIdFromCRSkuExtractor = new CollectionExtractor<Long, CustomerReviewSku>() {
        @Override
        protected Long convert(CustomerReviewSku customerReviewSku) {
            return customerReviewSku.getReviewId();
        }
    };

    private CollectionExtractor<Long, CustomerReviewDetail> reviewIdFromCRDetailExtractor = new CollectionExtractor<Long, CustomerReviewDetail>() {
        @Override
        protected Long convert(CustomerReviewDetail customerReviewDetail) {
            return customerReviewDetail.getReviewId();
        }
    };

    private CollectionExtractor<Long, CustomerReviewImg> reviewIdFromCRImgExtractor = new CollectionExtractor<Long, CustomerReviewImg>() {
        @Override
        protected Long convert(CustomerReviewImg customerReviewImg) {
            return customerReviewImg.getReviewId();
        }
    };


    @Override
    public ExecResult<CustomerReview> addNewCustomerReview(UserInfo userInfo) {
        CustomerReview customerReview = new CustomerReview(userInfo.getId(), userInfo.getUsername());
        customerReview = customerReviewDao.insert(customerReview);
        if (customerReview != null) {
            return ExecResult.succ(customerReview);
        }
        return ExecResult.fail("数据库添加用户评测失败");
    }

    @Override
    public ExecResult<CustomerReview> addNewCustomerReview(UserInfo userInfo, long itemId, long skuId) {
        CustomerReview customerReview = new CustomerReview(userInfo.getId(), userInfo.getUsername());
        return addNewCustomerReview(itemId, skuId, customerReview);
    }

    @Override
    public ExecResult<CustomerReview> addNewCustomerReview(UserInfo userInfo, long itemId, long skuId, String title,
                                                           String content, long skuStar, long shippingStar) {
        CustomerReview customerReview = new CustomerReview(title, userInfo.getId(), userInfo.getUsername(), skuStar, shippingStar);
        return addNewCustomerReview(itemId, skuId, customerReview);
    }

    private ExecResult<CustomerReview> addNewCustomerReview(long itemId, long skuId, CustomerReview customerReview) {
        customerReview = customerReviewDao.insert(customerReview);
        if (customerReview != null) {
            CustomerReviewSku customerReviewSku = new CustomerReviewSku(customerReview.getId(), itemId, skuId);
            customerReviewSkuDao.insert(customerReviewSku);
            return ExecResult.succ(customerReview);
        }
        return ExecResult.fail("数据库添加用户评测失败");
    }

    @Override
    public ExecInfo deleteNewCustomerReview(long reviewId) {
        if (customerReviewDao.delete(reviewId)) {
            customerReviewSkuDao.deleteByReviewId(reviewId);
            customerReviewImgDao.deleteByReviewId(reviewId);
            customerReviewDetailDao.deleteByReviewId(reviewId);
            return ExecInfo.succ();
        }
        return ExecInfo.fail("删除失败");
    }

    @Override
    public ExecInfo updateCustomerReview(long reviewId, String title, String content) {
        if (customerReviewDao.updateTitle(reviewId, title)) {
            customerReviewDetailDao.updateContent(reviewId, content);
            return ExecInfo.succ();
        }
        return ExecInfo.fail("更新失败");
    }

    @Override
    public ExecResult<String> uploadCustomerReviewImg(long reviewId, long itemId, long skuId, long userId, MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        String imgKey = getImgKey(itemId, skuId, userId);
        customerReviewImgDao.insert(new CustomerReviewImg(imgKey, reviewId, userId, itemId, skuId));
        LogUtils.info("img type: " + file.getContentType());
        LogUtils.info("img type: " + file.getOriginalFilename());
        ExecInfo execInfo = uploadImageToOss(file, is, imgKey);
        if (execInfo.isSucc()) {
            return ExecResult.succ(imgKey);
        }
        return ExecResult.fail(execInfo.getMsg());
    }

    @Override
    public List<CustomerReview> getByItemId(long itemId, PageInfo pageInfo) {
        List<CustomerReviewSku> customerReviewSkuList = customerReviewSkuDao.getByItemId(itemId, pageInfo);
        List<Long> reviewIdList = reviewIdFromCRSkuExtractor.extractList(customerReviewSkuList);
        List<CustomerReview> customerReviewList = customerReviewDao.getByReviewId(reviewIdList);
        List<CustomerReviewDetail> customerReviewDetailList = customerReviewDetailDao.getByReviewId(reviewIdList);
        List<CustomerReviewImg> customerReviewImgList = customerReviewImgDao.getByReviewId(reviewIdList);
        Map<Long, List<CustomerReviewSku>> reviewSkuMap = reviewIdFromCRSkuExtractor.extractListMap(customerReviewSkuList);
        Map<Long, CustomerReviewDetail> reviewDetailMap = reviewIdFromCRDetailExtractor.extractMap(customerReviewDetailList);
        Map<Long, List<CustomerReviewImg>> reviewImgMap = reviewIdFromCRImgExtractor.extractListMap(customerReviewImgList);
        for (CustomerReview customerReview : customerReviewList) {
            customerReview.setCustomerReviewDetail(reviewDetailMap.get(customerReview.getId()));
            customerReview.setCustomerReviewImgList(reviewImgMap.get(customerReview.getId()));
            customerReview.setCustomerReviewSkuList(reviewSkuMap.get(customerReview.getId()));
        }
        return customerReviewList;
    }

    private ExecInfo uploadImageToOss(MultipartFile file, InputStream is, String imgKey) {
        ExecInfo execInfo = ExecInfo.fail("上传云服务失败");
        try {
            execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        } catch (FileNotFoundException e) {
            System.out.println("上传图片文件未找到");
        }
        return execInfo;
    }

    private String getImgKey(long itemId, long skuId, long userId) {
        String imgKey = "images/item/itemReview/" + itemId + "/" + skuId + "/" + userId + "00" + System.currentTimeMillis();
        System.out.println("imgKey : " + imgKey);
        return imgKey;
    }
}
