package nanshen.dao;

import nanshen.data.Cart.CartGoods;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface CartGoodsDao {

    CartGoods insert(CartGoods goods);

    CartGoods get(long goodsId);

    List<CartGoods> getByUserId(long userId);

    List<CartGoods> getByCartId(long cartId);

    boolean update(CartGoods goods);

    boolean remove(long goodsId);

}
