package nanshen.service;

import nanshen.data.ExecResult;
import nanshen.data.Order;

import java.util.List;
import java.util.Map;

/**
 * Order related services
 *
 * @author WANG Minghao
 */
public interface OrderService {

    /**
     * get order by orderId
     *
     * @param orderId order id
     * @return Order
     */
    Order getByOrderId(long orderId);

    /**
     * get order by userId
     *
     * @param userId user id
     * @return List<Order>
     */
    List<Order> getByUserId(long userId);

    /**
     * get all the cart info
     *
     * @param userId user id
     * @param idList goods id list
     * @return Order
     */
    Order createOrder(long userId, List<Long> idList);

    /**
     * get all the cart info
     *
     * @param userId user id
     * @param idListString goods id list
     * @return Order
     */
    Order createOrder(long userId, String idListString);

    /**
     * start to pay order
     *
     * @param orderId the id of going to be payed order
     * @return ExecResult<Order>
     */
    ExecResult<Order> startPay(long orderId);

    /**
     * payed order
     *
     * @param orderId the id of just payed order
     * @return ExecResult<Order>
     */
    ExecResult<Order> payed(long orderId);

    /**
     * set up payment html
     *
     * @param order
     * @return
     */
    String getPaymentHtml(Order order);

    /**
     * update order status to paying
     *
     * @param orderId orderId
     * @return
     */
    boolean updateOrderToPaying(long orderId);

    /**
     * update order status to payed and record trade number
     *
     * @param out_trade_no showOrderId
     * @param trade_no alipay trade id
     * @param params alipay returned params
     * @return
     */
    boolean updateOrderToPayed(String out_trade_no, String trade_no, Map<String, String> params);

    /**
     * get order by showOrderId
     *
     * @param showOrderId showOrderId
     * @return
     */
    Order getByShowOrderId(String showOrderId);
}
