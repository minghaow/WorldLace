package nanshen.dao.impl;

import nanshen.dao.OrderLogDao;
import nanshen.dao.common.BaseDao;
import nanshen.data.OrderLog;
import nanshen.data.OrderOperation;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 记录订单相关操作日志的实现
 *
 * @author KONG Xiangxin
 */
@Repository
public class OrderLogDaoImpl extends BaseDao implements OrderLogDao {

    @Override
    public void log(long orderId, long buyerId, OrderOperation operation, String extraInfo) {
        if (extraInfo.length() > 100) {
            extraInfo = extraInfo.substring(0, 100);
        }
        OrderLog log = new OrderLog(orderId, buyerId, operation, extraInfo);
        dao.insert(log);
    }

    @Override
    public void log(long orderId, long buyerId, OrderOperation operation) {
        log(orderId, buyerId, operation, "");
    }

    @Override
    public List<OrderLog> getLogListByOrderId(long orderId) {
        Condition condition = Cnd
                .where("orderId", "=", orderId);
        return dao.query(OrderLog.class, condition);
    }

}
