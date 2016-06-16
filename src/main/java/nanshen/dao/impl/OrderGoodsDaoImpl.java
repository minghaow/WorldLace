package nanshen.dao.impl;

import nanshen.dao.OrderGoodsDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Order.OrderGoods;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WANG Minghao
 */
@Repository
public class OrderGoodsDaoImpl extends BaseDao implements OrderGoodsDao {

    @Override
    public OrderGoods insert(OrderGoods goods) {
        return dao.insert(goods);
    }

    @Override
    public OrderGoods get(long goodsId) {
        return dao.fetch(OrderGoods.class, goodsId);
    }

    @Override
    public List<OrderGoods> getByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId);
        return dao.query(OrderGoods.class, cnd);
    }

    @Override
    public List<OrderGoods> getByOrderId(long orderId) {
        Condition cnd = Cnd.where("orderId", "=", orderId);
        return dao.query(OrderGoods.class, cnd);
    }

    @Override
    public boolean update(OrderGoods goods) {
        return dao.update(goods) == 1;
    }

    @Override
    public boolean remove(long goodsId) {
        return dao.delete(OrderGoods.class, goodsId) == 1;
    }

    @Override
    public List<OrderGoods> getByOrderIdList(List<Long> orderIdList) {
        Condition cnd = Cnd.where("orderId", "in", orderIdList);
        return dao.query(OrderGoods.class, cnd);
    }

}
