package nanshen.dao.impl;

import nanshen.dao.SalesInfoDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Sku.SalesInfo;
import org.nutz.dao.Chain;
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
public class SalesInfoDaoImpl extends BaseDao implements SalesInfoDao {

    @Override
    public SalesInfo insert(SalesInfo salesInfo) {
        return dao.insert(salesInfo);
    }

    @Override
    public SalesInfo get(long skuId) {
        Condition condition = Cnd.where("skuId", "=", skuId);
        return dao.fetch(SalesInfo.class, condition);
    }

    @Override
    public List<SalesInfo> getBySkuItemId(long skuItemId) {
        Condition condition = Cnd.where("itemId", "=", skuItemId);
        return dao.query(SalesInfo.class, condition);
    }

    @Override
    public List<SalesInfo> get(List<Long> skuIdList) {
        Condition condition = Cnd.where("skuId", "in", skuIdList);
        return dao.query(SalesInfo.class, condition);
    }

    @Override
    public boolean update(SalesInfo salesInfo) {
        Condition condition = Cnd.where("skuId", "=", salesInfo.getSkuId());
        Chain chain = Chain
                .make("monthlyAmount", salesInfo.getMonthlyAmount())
                .add("totalAmount", salesInfo.getTotalAmount())
                .add("updateTime", new Date());
        return dao.update(SalesInfo.class, chain, condition) > 1;
    }

    @Override
    public boolean remove(long skuId) {
        Condition condition = Cnd.where("skuId", "in", skuId);
        return dao.clear(SalesInfo.class, condition) == 1;
    }
}
