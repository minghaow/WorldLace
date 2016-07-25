package nanshen.dao;

import nanshen.data.CustomerReview.CustomerReviewDetail;

import java.util.List;

/**
 * CustomerReviewDetail database DAO
 *
 * @author WANG Minghao
 */
public interface CustomerReviewDetailDao {

    CustomerReviewDetail insert(CustomerReviewDetail customerReviewDetail);

    boolean update(CustomerReviewDetail customerReviewDetail);

    boolean updateContent(long reviewId, String content);

    CustomerReviewDetail getByReviewId(long reviewId);

    List<CustomerReviewDetail> getByReviewId(List<Long> reviewIdList);

    CustomerReviewDetail get(long id);

    boolean delete(long id);

    boolean deleteByReviewId(long reviewId);
}
