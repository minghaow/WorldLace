package nanshen.dao.impl;

import nanshen.dao.CartDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Cart.Cart;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

/**
 * @author WANG Minghao
 */
@Repository
public class CartDaoImpl extends BaseDao implements CartDao {

    @Override
    public Cart insert(Cart cart) {
        return dao.insert(cart);
    }

    @Override
    public Cart get(long cartId) {
        return dao.fetch(Cart.class, cartId);
    }
    @Override
    public Cart getByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId);
        return dao.fetch(Cart.class, cnd);
    }

    @Override
    public boolean update(Cart cart) {
        return dao.update(cart) == 1;
    }

    @Override
    public boolean remove(long cartId) {
        return dao.delete(Cart.class, cartId) == 1;
    }

}
