package nanshen.dao;

import nanshen.data.OrderGoods;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface OrderGoodsDao {

    OrderGoods insert(OrderGoods goods);

    OrderGoods get(long goodsId);

    List<OrderGoods> getByUserId(long userId);

    List<OrderGoods> getByOrderId(long cartId);

    boolean update(OrderGoods goods);

    boolean remove(long goodsId);

}
