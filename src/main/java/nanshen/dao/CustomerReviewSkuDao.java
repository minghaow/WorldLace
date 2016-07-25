package nanshen.dao;

import nanshen.data.CustomerReview.CustomerReviewSku;
import nanshen.data.SystemUtil.PageInfo;

import java.util.List;

/**
 * CustomerReviewSku database DAO
 *
 * @author WANG Minghao
 */
public interface CustomerReviewSkuDao {

    CustomerReviewSku insert(CustomerReviewSku customerReviewSku);

    boolean update(CustomerReviewSku customerReviewSku);

    List<CustomerReviewSku> getBySkuId(long skuId);

    List<CustomerReviewSku> getByItemId(long itemId, PageInfo pageInfo);

    List<CustomerReviewSku> getByReviewId(long reviewId);

    CustomerReviewSku get(long id);

    boolean delete(long id);

    boolean deleteByReviewId(long reviewId);
}
