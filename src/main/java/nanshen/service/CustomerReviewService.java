package nanshen.service;

import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Customer review related services
 *
 * @author WANG Minghao
 */
public interface CustomerReviewService {

    /**
     * add new customer review to a sku
     *
     * @return ExecResult<CustomerReview>
     */
    ExecResult<CustomerReview> addNewCustomerReview(UserInfo userInfo);

    /**
     * add new customer review to a sku
     *
     * @return ExecResult<CustomerReview>
     */
    ExecResult<CustomerReview> addNewCustomerReview(UserInfo userInfo, long itemId, long skuId);

    /**
     * add new customer review to a sku
     *
     * @return ExecResult<CustomerReview>
     */
    ExecResult<CustomerReview> addNewCustomerReview(UserInfo userInfo, long itemId, long skuId, String title, String content,
                                                    long skuStar, long shippingStar);

    /**
     * delete customer review to a sku
     *
     * @return ExecInfo
     */
    ExecInfo deleteNewCustomerReview(long reviewId);

    /**
     * update customer review
     *
     * @return ExecInfo
     */
    ExecInfo updateCustomerReview(long reviewId, String title, String content);

    /**
     * upload review img
     *
     * @return img key
     */
    ExecResult<String> uploadCustomerReviewImg(long reviewId, long itemId, long skuId, long userId, MultipartFile file) throws IOException;

    /**
     * get customer review by item id
     *
     * @param itemId
     * @return
     */
    List<CustomerReview> getByItemId(long itemId, PageInfo pageInfo);
}
