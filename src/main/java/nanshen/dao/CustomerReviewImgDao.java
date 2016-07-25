package nanshen.dao;

import nanshen.data.CustomerReview.CustomerReviewImg;

import java.util.List;

/**
 * CustomerReviewImg database DAO
 *
 * @author WANG Minghao
 */
public interface CustomerReviewImgDao {

    CustomerReviewImg insert(CustomerReviewImg customerReviewImg);

    List<CustomerReviewImg> getByReviewId(long reviewId);

    List<CustomerReviewImg> getByReviewId(List<Long> reviewIdList);

    List<CustomerReviewImg> getBySkuId(long skuId);

    List<CustomerReviewImg> getByUserId(long userId);

    CustomerReviewImg get(long id);

    boolean delete(long id);

    boolean deleteByReviewId(long reviewId);
}
