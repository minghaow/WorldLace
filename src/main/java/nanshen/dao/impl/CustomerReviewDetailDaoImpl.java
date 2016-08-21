package nanshen.dao.impl;

import nanshen.dao.CustomerReviewDetailDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.CustomerReview.CustomerReviewDetail;
import org.nutz.dao.Chain;
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
public class CustomerReviewDetailDaoImpl extends BaseDao implements CustomerReviewDetailDao {
    @Override
    public CustomerReviewDetail insert(CustomerReviewDetail customerReviewDetail) {
        return dao.insert(customerReviewDetail);
    }

    @Override
    public boolean update(CustomerReviewDetail customerReviewDetail) {
        return dao.update(customerReviewDetail) == 1;
    }

    @Override
    public boolean updateContent(long reviewId, String content) {
        Chain chn = Chain
                .make("content", content);
        Condition cnd = Cnd
                .where("reviewId", "=", reviewId);
        return 1 == dao.update(CustomerReviewDetail.class, chn, cnd);
    }

    @Override
    public CustomerReviewDetail getByReviewId(long reviewId) {
        Condition condition = Cnd.where("reviewId", "=", reviewId);
        return dao.fetch(CustomerReviewDetail.class, condition);
    }

    @Override
    public List<CustomerReviewDetail> getByReviewId(List<Long> reviewIdList) {
        Condition condition = Cnd.where("reviewId", "in", reviewIdList);
        return dao.query(CustomerReviewDetail.class, condition);
    }

    @Override
    public CustomerReviewDetail get(long id) {
        return dao.fetch(CustomerReviewDetail.class, id);
    }

    @Override
    public boolean delete(long id) {
        return dao.delete(CustomerReviewDetail.class, id) == 1;
    }

    @Override
    public boolean deleteByReviewId(long reviewId) {
        Condition condition = Cnd.where("reviewId", "=", reviewId);
        return dao.clear(CustomerReviewDetail.class, condition) == 1;
    }
}
