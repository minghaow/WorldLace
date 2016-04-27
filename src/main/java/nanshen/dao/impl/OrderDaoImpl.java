package nanshen.dao.impl;

import nanshen.dao.OrderDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.Order;
import org.springframework.stereotype.Repository;

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
    public boolean update(Order order) {
        return dao.update(order) == 1;
    }

    @Override
    public boolean remove(long orderId) {
        return dao.delete(Order.class, orderId) == 1;
    }

}
