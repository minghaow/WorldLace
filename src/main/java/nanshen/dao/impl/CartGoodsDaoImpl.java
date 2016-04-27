package nanshen.dao.impl;

import nanshen.dao.CartGoodsDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.CartGoods;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WANG Minghao
 */
@Repository
public class CartGoodsDaoImpl extends BaseDao implements CartGoodsDao {

    @Override
    public CartGoods insert(CartGoods goods) {
        return dao.insert(goods);
    }

    @Override
    public CartGoods get(long goodsId) {
        return dao.fetch(CartGoods.class, goodsId);
    }

    @Override
    public List<CartGoods> getByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId);
        return dao.query(CartGoods.class, cnd);
    }

    @Override
    public List<CartGoods> getByCartId(long cartId) {
        Condition cnd = Cnd.where("cartId", "=", cartId);
        return dao.query(CartGoods.class, cnd);
    }

    @Override
    public boolean update(CartGoods goods) {
        return dao.update(goods) == 1;
    }

    @Override
    public boolean remove(long goodsId) {
        return dao.delete(CartGoods.class, goodsId) == 1;
    }

}
