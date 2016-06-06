package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.TimeConstants;
import nanshen.dao.CartDao;
import nanshen.dao.CartGoodsDao;
import nanshen.dao.OrderDao;
import nanshen.dao.OrderGoodsDao;
import nanshen.data.*;
import nanshen.service.CartService;
import nanshen.service.OrderService;
import nanshen.service.SkuService;
import nanshen.service.api.alipay.config.AlipayConfig;
import nanshen.service.api.alipay.util.AlipaySubmit;
import nanshen.service.common.ScheduledService;
import nanshen.utils.JsonUtils;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Order related service implementation
 *
 * @author WANG Minghao
 */
@Service
public class OrderServiceImpl extends ScheduledService implements OrderService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartGoodsDao cartGoodsDao;

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Autowired
    private CartService cartService;

    @Autowired
    private SkuService skuService;

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, Order> userCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, Order>() {
                        @Override
                        public Order load(Long orderId) throws Exception {
                            Order order = orderDao.get(orderId);
                            if (order != null) {
                                order.setGoodsList(orderGoodsDao.getByOrderId(orderId));
                                return order;
                            }
                            return null;
                        }
                    });

    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("[CartService] Update in " + totalTime + "ms");
    }

    @Override
    public int updatePeriod() {
        return TimeConstants.HOUR_IN_SECONDS;
    }

    @Override
    public Order getByOrderId(long orderId) {
        try {
            return userCache.get(orderId);
        } catch (ExecutionException e) {
            LogUtils.warning("OrderService: get user order info error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getByUserId(long userId) {
        return orderDao.getByUserId(userId);
    }

    @Override
    public Order createOrder(long userId, List<Long> idList) {
        Cart cart = cartService.getByUserId(userId);
        List<CartGoods> cartGoodsList = cart.getGoodsList();
        Order order = orderDao.insert(new Order(cart));
        List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
        for (CartGoods goods : cartGoodsList) {
            if (idList.contains(goods.getId())) {
                orderGoodsDao.insert(new OrderGoods(goods, order.getOrderId()));
                orderGoodsList.add(new OrderGoods(goods, order.getOrderId()));
                order.setGoodsCount(order.getGoodsCount() + goods.getCount());
                order.setTotalPrice(order.getTotalPrice() + goods.getPrice());
                order.setDiscountPrice(order.getDiscountPrice() + goods.getDiscountPrice());
                cartService.deleteGoods(userId, goods.getId());
            }
        }
        order.setGoodsList(orderGoodsList);
        orderDao.update(order);
        return order;
    }

    @Override
    public Order createOrder(long userId, String idListString) {
        List<String> skuIdStringList = Arrays.asList(idListString.split(","));
        List<Long> skuIdList = new ArrayList<Long>();
        for (String skuIdString : skuIdStringList) {
            skuIdList.add(Long.parseLong(skuIdString));
        }
        Order order = createOrder(userId, skuIdList);
        if (order != null) {
            orderLogDao.log(order.getOrderId(), 0, OrderOperation.CREATE_ORDER, "总价：" + order.getTotalPriceString()  + "商品ID列表：" + idListString);
        }
        return order;
    }

    @Override
    public ExecResult<Order> startPay(long orderId) {
        Order order = getByOrderId(orderId);
        if (order.getOrderStatus() != OrderStatus.NEW) {
            return ExecResult.fail("订单已经处于" + order.getOrderStatus().getDesc() + "状态！");
        }
        if (orderDao.updateStatus(orderId, Collections.singletonList(OrderStatus.NEW), OrderStatus.PAYING)) {
            return ExecResult.succ(order);
        }
        return ExecResult.fail("修改订单状态到支付中失败，请联系客服");
    }

    @Override
    public ExecResult<Order> payed(long orderId) {
        Order order = getByOrderId(orderId);
        if (order.getOrderStatus() != OrderStatus.PAYING) {
            return ExecResult.fail("订单已经处于" + order.getOrderStatus().getDesc() + "状态！");
        }
        if (orderDao.updateStatus(orderId, Collections.singletonList(OrderStatus.PAYING), OrderStatus.PAYED)) {
            return ExecResult.succ(order);
        }
        return ExecResult.fail("修改订单状态到已支付失败，请联系客服");
    }

    @Override
    public String getPaymentHtml(Order order) {
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", AlipayConfig.notify_url);
        sParaTemp.put("return_url", AlipayConfig.return_url);
        sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        sParaTemp.put("out_trade_no", ((Long)order.getShowOrderId()).toString());
        sParaTemp.put("subject", "在桃源餐厨用品共" + order.getGoodsCount() + "件");
        sParaTemp.put("total_fee", order.getTotalPriceString());
        sParaTemp.put("body", "高端品质餐厨用品。");

        //建立请求
        return AlipaySubmit.buildRequest(sParaTemp, "get", "确认支付");
    }

    @Override
    public boolean updateOrderToPayed(String out_trade_no, String trade_no, Map<String, String> params) {
        Order order = getByShowOrderId(out_trade_no);
        if (orderDao.updateStatusToPayed(out_trade_no, trade_no)) {
            orderLogDao.log(order.getOrderId(), 0, OrderOperation.ORDER_PAYED, "Trade No. [" + trade_no + "]");
            orderLogDao.log(order.getOrderId(), 0, OrderOperation.ALIPAY_FEEDBACK, JsonUtils.toJson(params));
            return true;
        }
        return false;
    }

    @Override
    public Order getByShowOrderId(String showOrderId) {
        return orderDao.getByShowOrderId(showOrderId);
    }

}
