package nanshen.dao.impl;

import nanshen.dao.CustomerReviewSkuDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.CustomerReview.CustomerReviewSku;
import nanshen.data.SystemUtil.PageInfo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class CustomerReviewSkuDaoImpl extends BaseDao implements CustomerReviewSkuDao {
    @Override
    public CustomerReviewSku insert(CustomerReviewSku customerReviewSku) {
        return dao.insert(customerReviewSku);
    }

    @Override
    public boolean update(CustomerReviewSku customerReviewSku) {
        return dao.update(customerReviewSku) == 1;
    }

    @Override
    public List<CustomerReviewSku> getBySkuId(long skuId) {
        Condition condition = Cnd.where("skuId", "=", skuId).desc("createTime");
        return dao.query(CustomerReviewSku.class, condition);
    }

    @Override
    public List<CustomerReviewSku> getByItemId(long itemId, PageInfo pageInfo) {
        Condition condition = Cnd.where("itemId", "=", itemId).desc("createTime");
        return dao.query(CustomerReviewSku.class, condition, genaratePager(pageInfo));
    }

    @Override
    public List<CustomerReviewSku> getByReviewId(long reviewId) {
        Condition condition = Cnd.where("reviewId", "=", reviewId);
        return dao.query(CustomerReviewSku.class, condition);
    }

    @Override
    public CustomerReviewSku get(long id) {
        return dao.fetch(CustomerReviewSku.class, id);
    }

    @Override
    public boolean delete(long id) {
        return dao.delete(CustomerReviewSku.class, id) == 1;
    }

    @Override
    public boolean deleteByReviewId(long reviewId) {
        Condition condition = Cnd.where("reviewId", "=", reviewId);
        return dao.clear(CustomerReviewSku.class, condition) > 0;
    }
}
