package nanshen.dao;

import nanshen.data.Order.Order;
import nanshen.data.Order.OrderStatus;
import nanshen.data.SystemUtil.PageInfo;

import java.util.List;

/**
 * @author WANG Minghao
 */
public interface OrderDao {

    Order insert(Order order);

    Order get(long orderId);

    boolean updateStatus(long orderId, List<OrderStatus> from, OrderStatus to);

    boolean update(Order order);

    boolean remove(long orderId);

    boolean changeAddress(long orderId, long addressId);

    List<Order> getByUserId(long userId);

    boolean updateStatusToPayed(String out_trade_no, String trade_no);

    Order getByShowOrderId(String showOrderId);

    boolean updateStatusToPaying(long orderId, long addressId);

    List<Order> getAll(OrderStatus status, PageInfo pageInfo);
}
