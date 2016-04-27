package nanshen.dao;

import nanshen.data.Order;

/**
 * @author WANG Minghao
 */
public interface OrderDao {

    Order insert(Order order);

    Order get(long orderId);

    boolean update(Order order);

    boolean remove(long orderId);

}
