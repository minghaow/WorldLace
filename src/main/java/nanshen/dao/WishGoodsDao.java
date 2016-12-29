package nanshen.dao;

import nanshen.data.Wish.WishGoods;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface WishGoodsDao {

    WishGoods insert(WishGoods goods);

    WishGoods get(long goodsId);

    List<WishGoods> getByUserId(long userId);

    List<WishGoods> getByCartId(long cartId);

    boolean update(WishGoods goods);

    boolean remove(long goodsId);

}
