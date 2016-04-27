package nanshen.dao.impl;

import nanshen.dao.SkuInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.PageInfo;
import nanshen.data.PublicationStatus;
import nanshen.data.SkuItem;
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
public class SkuInfoDaoImpl extends BaseDao implements SkuInfoDao {

    @Override
    public SkuItem insert(SkuItem skuItem) {
        return dao.insert(skuItem);
    }

    @Override
    public SkuItem get(long skuId) {
        return dao.fetch(SkuItem.class, skuId);
    }

    @Override
    public boolean update(SkuItem skuItem) {
        return dao.update(skuItem) == 1;
    }

    @Override
    public boolean remove(long skuId) {
        return dao.delete(SkuItem.class, skuId) == 1;
    }

    @Override
    public List<SkuItem> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01").desc("id");
        return dao.query(SkuItem.class, cnd);
    }

    @Override
    public List<SkuItem> getAll(PublicationStatus status) {
        Condition cnd = Cnd.where("status", "=", status).desc("id");
        return dao.query(SkuItem.class, cnd);
    }

    @Override
    public long getCnt(PublicationStatus status) {
        Condition cnd = Cnd.where("status", "=", status).desc("id");
        return dao.count(SkuItem.class, cnd);
    }

    @Override
    public long getCnt(PublicationStatus status, Date startDate) {
        Condition cnd = Cnd.where("status", "=", status)
                .and("createTime", ">", startDate)
                .desc("id");
        return dao.count(SkuItem.class, cnd);
    }

    @Override
    public List<SkuItem> getByLookId(long lookId) {
        Condition cnd = Cnd.where("lookId", "=", lookId)
                .desc("id");
        return dao.query(SkuItem.class, cnd);
    }

    @Override
    public List<SkuItem> getAll(PublicationStatus status, PageInfo pageInfo) {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01")
                .and("status", "=", status).desc("id");
        return dao.query(SkuItem.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public boolean remove(long lookId, long operatorId) {
        Condition cnd = Cnd.where("id", "=", lookId);
        return dao.clear(SkuItem.class, cnd) == 1;
    }
}
