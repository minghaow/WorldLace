package nanshen.dao.impl;

import nanshen.dao.CustomerReviewImgDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.CustomerReview.CustomerReviewImg;
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
public class CustomerReviewImgDaoImpl extends BaseDao implements CustomerReviewImgDao {
    @Override
    public CustomerReviewImg insert(CustomerReviewImg customerReviewImg) {
        return dao.insert(customerReviewImg);
    }

    @Override
    public List<CustomerReviewImg> getByReviewId(long reviewId) {
        Condition condition = Cnd.where("reviewId", "=", reviewId);
        return dao.query(CustomerReviewImg.class, condition);
    }

    @Override
    public List<CustomerReviewImg> getByReviewId(List<Long> reviewIdList) {
        Condition condition = Cnd.where("reviewId", "in", reviewIdList);
        return dao.query(CustomerReviewImg.class, condition);
    }

    @Override
    public List<CustomerReviewImg> getBySkuId(long skuId) {
        Condition condition = Cnd.where("skuId", "=", skuId);
        return dao.query(CustomerReviewImg.class, condition);
    }

    @Override
    public List<CustomerReviewImg> getByUserId(long userId) {
        Condition condition = Cnd.where("userId", "=", userId);
        return dao.query(CustomerReviewImg.class, condition);
    }

    @Override
    public CustomerReviewImg get(long id) {
        return dao.fetch(CustomerReviewImg.class, id);
    }

    @Override
    public boolean delete(long id) {
        return dao.delete(CustomerReviewImg.class, id) == 1;
    }

    @Override
    public boolean deleteByReviewId(long reviewId) {
        Condition condition = Cnd.where("reviewId", "=", reviewId);
        return dao.clear(CustomerReviewImg.class, condition) > 0;
    }
}
