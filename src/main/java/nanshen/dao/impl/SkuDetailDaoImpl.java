package nanshen.dao.impl;

import nanshen.dao.SkuDetailDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.*;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class SkuDetailDaoImpl extends BaseDao implements SkuDetailDao {

    @Override
    public SkuDetail insert(SkuDetail skuDetail) {
        return dao.insert(skuDetail);
    }

    @Override
    public List<SkuDetail> get(long itemId) {
        Condition cnd = Cnd.where("itemId", "=", itemId);
        return dao.query(SkuDetail.class, cnd);
    }

    @Override
    public boolean update(SkuItem skuItem) {
        return dao.delete(SkuDetail.class, skuItem.getId()) == 1;
    }

    @Override
    public boolean remove(long itemId) {
        Condition cnd = Cnd.where("itemId", "=", itemId);
        return dao.clear(SkuDetail.class, cnd) == 1;
    }

    @Override
    public List<SkuDetail> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01").desc("id");
        return dao.query(SkuDetail.class, cnd);
    }

    @Override
    public List<SkuDetail> getAll(PublicationStatus status) {
        Condition cnd = Cnd
                .where("createTime", ">", "2015-06-01")
                .and("status", "=", status)
                .desc("id");
        return dao.query(SkuDetail.class, cnd);
    }

    @Override
    public List<SkuDetail> getAll(PublicationStatus status, PageInfo pageInfo) {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01")
                .and("status", "=", status).desc("id");
        return dao.query(SkuDetail.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public boolean remove(long itemId, long operatorId) {
        Condition cnd = Cnd
                .where("itemId", "=", itemId)
                .and("operatorId", "=", operatorId);
        return dao.clear(SkuDetail.class, cnd) == 1;
    }

    @Override
    public long getCnt(PublicationStatus status) {
        Condition cnd = Cnd.where("status", "=", status);
        return dao.count(LookInfo.class, cnd);
    }

    @Override
    public long getCnt(PublicationStatus status, Date startDate) {
        Condition cnd = Cnd.where("status", "=", status)
                .and("createTime", ">", startDate)
                .desc("id");
        return dao.count(SkuDetail.class, cnd);
    }
}
