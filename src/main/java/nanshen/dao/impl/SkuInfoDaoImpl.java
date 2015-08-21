package nanshen.dao.impl;

import nanshen.dao.SkuInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.PageInfo;
import nanshen.data.PublicationStatus;
import nanshen.data.SkuInfo;
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
    public SkuInfo insert(SkuInfo skuInfo) {
        return dao.insert(skuInfo);
    }

    @Override
    public SkuInfo get(long skuId) {
        return dao.fetch(SkuInfo.class, skuId);
    }

    @Override
    public boolean update(SkuInfo skuInfo) {
        return dao.update(skuInfo) == 1;
    }

    @Override
    public boolean remove(long skuId) {
        return dao.delete(SkuInfo.class, skuId) == 1;
    }

    @Override
    public List<SkuInfo> getAll() {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01").desc("id");
        return dao.query(SkuInfo.class, cnd);
    }

    @Override
    public List<SkuInfo> getAll(PublicationStatus status) {
        Condition cnd = Cnd.where("status", "=", status).desc("id");
        return dao.query(SkuInfo.class, cnd);
    }

    @Override
    public long getCnt(PublicationStatus status) {
        Condition cnd = Cnd.where("status", "=", status).desc("id");
        return dao.count(SkuInfo.class, cnd);
    }

    @Override
    public long getCnt(PublicationStatus status, Date startDate) {
        Condition cnd = Cnd.where("status", "=", status)
                .and("createTime", ">", startDate)
                .desc("id");
        return dao.count(SkuInfo.class, cnd);
    }

    @Override
    public List<SkuInfo> getByLookId(long lookId) {
        Condition cnd = Cnd.where("lookId", "=", lookId)
                .desc("id");
        return dao.query(SkuInfo.class, cnd);
    }

    @Override
    public List<SkuInfo> getAll(PublicationStatus status, PageInfo pageInfo) {
        Condition cnd = Cnd.where("createTime", ">", "2015-06-01")
                .and("status", "=", status).desc("id");
        return dao.query(SkuInfo.class, cnd, genaratePager(pageInfo));
    }

    @Override
    public boolean remove(long lookId, long operatorId) {
        Condition cnd = Cnd.where("id", "=", lookId);
        return dao.clear(SkuInfo.class, cnd) == 1;
    }
}
