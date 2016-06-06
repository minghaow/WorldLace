package nanshen.dao.impl;

import nanshen.dao.OrderDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Order;
import nanshen.data.OrderStatus;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao {

    @Override
    public Order insert(Order order) {
        return dao.insert(order);
    }

    @Override
    public Order get(long orderId) {
        return dao.fetch(Order.class, orderId);
    }

    @Override
    public boolean updateStatus(long orderId, List<OrderStatus> from, OrderStatus to) {
        Chain chain = Chain
                .make("orderStatus", to)
                .add("updateTime", new Date());
        Cnd cnd = Cnd
                .where("orderId", "=", orderId)
                .and("orderStatus", "in", from);
        return 1 == dao.update(Order.class, chain, cnd);
    }

    @Override
    public boolean update(Order order) {
        return dao.update(order) == 1;
    }

    @Override
    public boolean remove(long orderId) {
        return dao.delete(Order.class, orderId) == 1;
    }

    @Override
    public boolean changeAddress(long orderId, long addressId) {
        Chain chain = Chain
                .make("addressId", addressId)
                .add("updateTime", new Date());
        Cnd cnd = Cnd
                .where("orderId", "=", orderId);
        return 1 == dao.update(Order.class, chain, cnd);
    }

    @Override
    public List<Order> getByUserId(long userId) {
        Condition cnd = Cnd.where("userId", "=", userId).orderBy("orderId", "dsc");
        return dao.query(Order.class, cnd);
    }

    @Override
    public boolean updateStatusToPayed(String out_trade_no, String trade_no) {
        Chain chain = Chain
                .make("tradeNo", trade_no)
                .add("orderStatus", OrderStatus.PAYED)
                .add("updateTime", new Date());
        Cnd cnd = Cnd
                .where("showOrderId", "=", out_trade_no)
                .and("orderStatus", "=", OrderStatus.NEW);
        return 1 == dao.update(Order.class, chain, cnd);
    }

    @Override
    public Order getByShowOrderId(String showOrderId) {
        Condition cnd = Cnd.where("showOrderId", "=", showOrderId);
        return dao.fetch(Order.class, cnd);
    }

}
