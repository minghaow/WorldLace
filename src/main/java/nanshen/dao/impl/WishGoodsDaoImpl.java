package nanshen.dao.impl;

import nanshen.dao.WishGoodsDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Wish.WishGoods;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WANG Minghao
 */
@Repository
public class WishGoodsDaoImpl extends BaseDao implements WishGoodsDao {

    @Override
    public WishGoods insert(WishGoods goods) {
        return dao.insert(goods);
    }

    @Override
    public WishGoods get(long goodsId) {
        return dao.fetch(WishGoods.class, goodsId);
    }

    @Override
    public List<WishGoods> getByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId);
        return dao.query(WishGoods.class, cnd);
    }

    @Override
    public List<WishGoods> getByCartId(long cartId) {
        Condition cnd = Cnd.where("cartId", "=", cartId);
        return dao.query(WishGoods.class, cnd);
    }

    @Override
    public boolean update(WishGoods goods) {
        return dao.update(goods) == 1;
    }

    @Override
    public boolean remove(long goodsId) {
        return dao.delete(WishGoods.class, goodsId) == 1;
    }

}
