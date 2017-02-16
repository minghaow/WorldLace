package nanshen.dao.impl;

import nanshen.dao.SkuItemDescriptionDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Sku.SkuItemDescription;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class SkuItemDescriptionDaoImpl extends BaseDao implements SkuItemDescriptionDao {

    @Override
    public SkuItemDescription insert(long itemId) {
        return dao.insert(new SkuItemDescription(itemId));
    }

    @Override
    public boolean update(SkuItemDescription skuItemDescription) {
        return dao.update(skuItemDescription) == 1;
    }

    @Override
    public SkuItemDescription get(long itemId) {
        Condition cnd = Cnd.where("itemId", "=", itemId);
        return dao.fetch(SkuItemDescription.class, cnd);
    }

}
