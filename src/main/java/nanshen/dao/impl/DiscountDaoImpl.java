package nanshen.dao.impl;

import nanshen.dao.DiscountDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Discount.Discount;
import org.nutz.dao.Cnd;
import org.springframework.stereotype.Repository;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
@Repository
public class DiscountDaoImpl extends BaseDao implements DiscountDao {
    @Override
    public Discount insert(Discount discount) {
        return dao.insert(discount);
    }

    @Override
    public Discount get(String code) {
        return dao.fetch(Discount.class, Cnd.where("code", "=", code));
    }

    @Override
    public boolean update(Discount discount) {
        return dao.update(discount) == 1;
    }
}
