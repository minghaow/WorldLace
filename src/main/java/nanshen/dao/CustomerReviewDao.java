package nanshen.dao;

import nanshen.data.CustomerReview.CustomerReview;

import java.util.List;

/**
 * CustomerReview database DAO
 *
 * @author WANG Minghao
 */
public interface CustomerReviewDao {

    CustomerReview insert(CustomerReview customerReview);

    boolean update(CustomerReview customerReview);

    boolean updateTitle(long reviewId, String title);

    boolean addView(long reviewId);

    boolean addView(long reviewId, long amount);

    boolean addLike(long reviewId);

    boolean addLike(long reviewId, long amount);

    boolean deleteLike(long reviewId);

    List<CustomerReview> getBySku(long skuId);

    List<CustomerReview> getBySkuAndUserId(long skuId, long userId);

    CustomerReview getByReviewId(long reviewId);

    List<CustomerReview> getByReviewId(List<Long> reviewIdList);

    CustomerReview get(long reviewId);

    boolean delete(long reviewId);

    boolean publish(long reviewId);

    CustomerReview getByUserIdAndOrderId(long userId, long orderId);
}
