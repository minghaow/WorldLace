package nanshen.dao.impl;

import nanshen.dao.CustomerReviewDao;
import nanshen.dao.CustomerReviewSkuDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.CustomerReview.CustomerReviewSku;
import nanshen.utils.CollectionExtractor;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class CustomerReviewDaoImpl extends BaseDao implements CustomerReviewDao {

    @Autowired
    private CustomerReviewSkuDao customerReviewSkuDao;

    private CollectionExtractor<Long, CustomerReviewSku> reviewIdExtractor = new CollectionExtractor<Long, CustomerReviewSku>() {
        @Override
        protected Long convert(CustomerReviewSku customerReviewSku) {
            return customerReviewSku.getReviewId();
        }
    };

    @Override
    public CustomerReview insert(CustomerReview customerReview) {
        if (customerReview == null) {
            return null;
        }
        return dao.insert(customerReview);
    }

    @Override
    public boolean update(CustomerReview customerReview) {
        return customerReview != null && dao.update(customerReview) == 1;
    }

    @Override
    public boolean updateTitle(long reviewId, String title) {
        Chain chn = Chain
                .make("title", title)
                .add("updateTime", new Date());
        Condition cnd = Cnd
                .where("id", "=", reviewId);
        return 1 == dao.update(CustomerReview.class, chn, cnd);
    }

    @Override
    public boolean addView(long reviewId) {
        Sql sql = Sqls.create("UPDATE CustomerReview SET view = view + 1 WHERE id = @id");
        sql.params().set("id", reviewId);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public boolean addView(long reviewId, long amount) {
        Sql sql = Sqls.create("UPDATE CustomerReview SET view = view + @amount WHERE id = @id");
        sql.params().set("amount", amount);
        sql.params().set("id", reviewId);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public boolean addLike(long reviewId) {
        Sql sql = Sqls.create("UPDATE CustomerReview SET like = like + 1 WHERE id = @id");
        sql.params().set("id", reviewId);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public boolean addLike(long reviewId, long amount) {
        Sql sql = Sqls.create("UPDATE CustomerReview SET like = like + @amount WHERE id = @id");
        sql.params().set("amount", amount);
        sql.params().set("id", reviewId);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public boolean deleteLike(long reviewId) {
        Sql sql = Sqls.create("UPDATE CustomerReview SET like = like - 1 WHERE id = @id");
        sql.params().set("id", reviewId);
        dao.execute(sql);
        return 1 == sql.getUpdateCount();
    }

    @Override
    public List<CustomerReview> getBySku(long skuId) {
        List<CustomerReviewSku> reviewSkuList = customerReviewSkuDao.getBySkuId(skuId);
        if (reviewSkuList == null || reviewSkuList.size() == 0) {
            return new ArrayList<CustomerReview>();
        }
        List<Long> reviewIdList = reviewIdExtractor.extractList(reviewSkuList);
        Condition condition = Cnd.where("id", "in", reviewIdList);
        return dao.query(CustomerReview.class, condition);
    }

    @Override
    public CustomerReview getByReviewId(long reviewId) {
        List<CustomerReview> customerReviewList = getByReviewId(Collections.singletonList(reviewId));
        if (customerReviewList != null && customerReviewList.size() > 0) {
            return customerReviewList.get(0);
        }
        return null;
    }

    @Override
    public List<CustomerReview> getByReviewId(List<Long> reviewIdList) {
        Condition condition = Cnd.where("id", "in", reviewIdList);
        return dao.query(CustomerReview.class, condition);
    }

    @Override
    public List<CustomerReview> getBySkuAndUserId(long skuId, long userId) {
        List<CustomerReviewSku> reviewSkuList = customerReviewSkuDao.getBySkuId(skuId);
        if (reviewSkuList == null || reviewSkuList.size() == 0) {
            return new ArrayList<CustomerReview>();
        }
        List<Long> reviewIdList = reviewIdExtractor.extractList(reviewSkuList);
        Condition condition = Cnd.where("id", "in", reviewIdList).and("userId", "=", userId);
        return dao.query(CustomerReview.class, condition);
    }

    @Override
    public CustomerReview get(long reviewId) {
        return dao.fetch(CustomerReview.class, reviewId);
    }

    @Override
    public boolean delete(long reviewId) {
        return dao.delete(CustomerReview.class, reviewId) == 1;
    }

    @Override
    public boolean publish(long reviewId) {
        Chain chn = Chain
                .make("isPublished", true)
                .add("updateTime", new Date());
        Condition cnd = Cnd
                .where("id", "=", reviewId);
        return 1 == dao.update(CustomerReview.class, chn, cnd);
    }

}
