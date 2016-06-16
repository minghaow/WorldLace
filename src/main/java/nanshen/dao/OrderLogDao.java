package nanshen.dao;

import nanshen.data.Order.OrderLog;
import nanshen.data.Order.OrderOperation;

import java.util.List;

/**
 * 记录订单相关操作日志
 *
 * @author WANG Minghao
 */
public interface OrderLogDao {

    public void log(long orderId, long buyerId, OrderOperation operation, String extraInfo);

    public void log(long orderId, long buyerId, OrderOperation operation);

    /**
     * 取到某订单的所有历史操作记录
     *
     * @param orderId
     * @return
     */
    public List<OrderLog> getLogListByOrderId(long orderId);

}
