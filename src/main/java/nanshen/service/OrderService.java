package nanshen.service;

import nanshen.data.Order.Order;
import nanshen.data.Order.OrderStatus;
import nanshen.data.Sku.SkuCommentImg;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserAddress;
import org.springframework.web.multipart.MultipartFile;

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
     * @param userAddress user address
     * @return
     */
    boolean updateOrderToPaying(long orderId, UserAddress userAddress);

    /**
     * update order status to payed and record trade number
     *
     * @param out_trade_no showOrderId
     * @param trade_no alipay trade id
     * @param params alipay returned params
     * @return boolean
     */
    boolean updateOrderToPayed(String out_trade_no, String trade_no, Map<String, String> params);

    /**
     * get order by showOrderId
     *
     * @param showOrderId showOrderId
     * @return Order
     */
    Order getByShowOrderId(String showOrderId);

    /**
     * finish order by orderId
     *
     * @param orderId orderId
     * @return boolean
     */
    boolean finish(long orderId);

    /**
     * comment order by orderId and skuId
     *
     * @param orderId orderId
     * @param skuId skuId
     * @param star the star number user give to sku
     * @param comment user comment
     * @param imgList user uploaded img
     * @return ExecInfo
     */
    ExecInfo comment(long orderId, long skuId, long star, String comment, List<Long> imgList);

    /**
     * comment order by orderId and skuId
     *
     * @param userId user id
     * @param skuId sku id
     * @param file image file
     * @return ExecResult<SkuCommentImg>
     */
    ExecResult<SkuCommentImg> uploadCommentImg(long userId, long skuId, MultipartFile file);

    /**
     * get all order info by status and page
     *
     * @param status order status
     * @param pageInfo page number
     * @return
     */
    List<Order> getAll(OrderStatus status, PageInfo pageInfo);

    /**
     * get all order info by status and page with user name
     *
     * @param status order status
     * @param pageInfo page number
     * @return List<Order>
     */
    List<Order> getAllForOrderList(OrderStatus status, PageInfo pageInfo);

    /**
     * get all order info by orderId with username and goods
     *
     * @param orderId orderId
     * @return Order
     */
    Order getByOrderIdWithAllInfo(long orderId);

    /**
     * clear order related cache
     *
     * @return boolean
     */
    boolean clearOrderCache();
}
