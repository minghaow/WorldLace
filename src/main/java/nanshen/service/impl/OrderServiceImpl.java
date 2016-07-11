package nanshen.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import nanshen.constant.SystemConstants;
import nanshen.constant.TimeConstants;
import nanshen.dao.*;
import nanshen.data.AdminUserInfo;
import nanshen.data.Cart.Cart;
import nanshen.data.Cart.CartGoods;
import nanshen.data.Order.*;
import nanshen.data.Sku.SkuComment;
import nanshen.data.Sku.SkuCommentImg;
import nanshen.data.SystemUtil.ExecInfo;
import nanshen.data.SystemUtil.ExecResult;
import nanshen.data.SystemUtil.PageInfo;
import nanshen.data.User.UserAddress;
import nanshen.data.User.UserInfo;
import nanshen.service.*;
import nanshen.service.api.alipay.config.AlipayConfig;
import nanshen.service.api.alipay.util.AlipaySubmit;
import nanshen.service.api.oss.OssFormalApi;
import nanshen.service.common.ScheduledService;
import nanshen.utils.CollectionExtractor;
import nanshen.utils.JsonUtils;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    private UserInfoDao userInfoDao;

    @Autowired
    private CartGoodsDao cartGoodsDao;

    @Autowired
    private SkuCommentDao skuCommentDao;

    @Autowired
    private SkuCommentImgDao skuCommentImgDao;

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private AdminUserInfoDao adminUserInfoDao;

    @Autowired
    private OssFormalApi ossFormalApi;

    @Autowired
    private CartService cartService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private AccountService accountService;

    private CollectionExtractor<Long, Order> orderExtractor = new CollectionExtractor<Long, Order>() {
        @Override
        protected Long convert(Order order) {
            return order.getOrderId();
        }
    };

    private CollectionExtractor<Long, OrderGoods> orderGoodsExtractor = new CollectionExtractor<Long, OrderGoods>() {
        @Override
        protected Long convert(OrderGoods order) {
            return order.getOrderId();
        }
    };

    private CollectionExtractor<Long, Order> userIdExtractor = new CollectionExtractor<Long, Order>() {
        @Override
        protected Long convert(Order order) {
            return order.getUserId();
        }
    };

    private CollectionExtractor<Long, UserInfo> userInfoExtractor = new CollectionExtractor<Long, UserInfo>() {
        @Override
        protected Long convert(UserInfo userInfo) {
            return userInfo.getId();
        }
    };

    private CollectionExtractor<Long, OrderLog> orderLogExtractor = new CollectionExtractor<Long, OrderLog>() {
        @Override
        protected Long convert(OrderLog orderLog) {
            return orderLog.getUserId();
        }
    };

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, Order> orderCache = CacheBuilder.newBuilder()
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

    /** 买手ID到买手信息的缓存 */
    private final LoadingCache<Long, List<Order>> orderUserIdCache = CacheBuilder.newBuilder()
            .softValues()
            .expireAfterWrite(TimeConstants.HALF_HOUR_IN_SECONDS, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<Long, List<Order>>() {
                        @Override
                        public List<Order> load(Long userId) throws Exception {
                            List<Order> orderList = orderDao.getByUserId(userId);
                            if (orderList != null && orderList.size() > 0) {
                                for (Order order : orderList) {
                                    order.setGoodsList(orderGoodsDao.getByOrderId(order.getOrderId()));
                                }
                                return orderList;
                            }
                            return new ArrayList<Order>();
                        }
                    });

    @Override
    public void update() {
        long startTime = System.currentTimeMillis();

//        closeInvalidPayingOrders();

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
            return orderCache.get(orderId);
        } catch (ExecutionException e) {
            LogUtils.warning("OrderService: get user order info error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getByUserId(long userId) {
        try {
            return orderUserIdCache.get(userId);
        } catch (ExecutionException e) {
            LogUtils.warning("OrderService: get user order info error!", e);
            throw new RuntimeException(e);
        }
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
                order.setTotalPrice(order.getTotalPrice() + goods.getPrice() * goods.getCount());
                order.setDiscountPrice(order.getDiscountPrice() + goods.getDiscountPrice());
                cartService.deleteGoods(userId, goods.getId());
            }
        }
        order.setGoodsList(orderGoodsList);
        orderDao.update(order);
        orderUserIdCache.invalidate(userId);
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
            orderLogDao.log(order.getOrderId(), 0, OrderOperation.CREATE_ORDER, "总价：" + order.getTotalPriceString()  + " 商品ID列表：" + idListString);
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
            orderUserIdCache.invalidate(order.getUserId());
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
            orderUserIdCache.invalidate(order.getUserId());
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
    public boolean updateOrderToPaying(long orderId, UserAddress userAddress) {
        Order order = getByOrderId(orderId);
        UserAddress userAddressResult = userAddressService.createAddress(userAddress);
        if (orderDao.updateStatusToPaying(orderId, userAddressResult.getId())) {
            orderUserIdCache.invalidate(order.getUserId());
            orderLogDao.log(orderId, 0, OrderOperation.ORDER_PAYING, "地址ID: " + userAddressResult.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOrderToPayed(String out_trade_no, String trade_no, Map<String, String> params) {
        Order order = getByShowOrderId(out_trade_no);
        if (orderDao.updateStatusToPayed(out_trade_no, trade_no)) {
            orderUserIdCache.invalidate(order.getUserId());
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

    @Override
    public boolean finish(long orderId) {
        if (orderDao.updateStatus(orderId, Arrays.asList(OrderStatus.SHIPPING, OrderStatus.CONFIRMED), OrderStatus.FINISHED)) {
            orderUserIdCache.invalidate(orderId);
            orderLogDao.log(orderId, 0, OrderOperation.USER_FINISH);
            return true;
        }
        return false;
    }

    @Override
    public ExecInfo comment(long orderId, long skuId, long star, String comment, List<Long> imgList) {
        Order order = getByOrderId(orderId);
        if (order.getOrderStatus() != OrderStatus.FINISHED) {
            return ExecInfo.fail("请确认收货后再评论");
        }
        SkuComment skuComment = skuCommentDao.insert(new SkuComment(comment, imgList.size(), skuId, star, order.getUserId()));
        if (!skuCommentImgDao.updateImgCommentId(order.getUserId(), skuId, imgList, skuComment.getId())) {
            return ExecInfo.fail("图片更新错误，请勿使用错误接口");
        }
        return ExecInfo.succ();
    }

    @Override
    public ExecResult<SkuCommentImg> uploadCommentImg(long userId, long skuId, MultipartFile file) {
        String imgKey = getUserCommentImgKey(skuId, userId);
        try {
            InputStream is = file.getInputStream();
            uploadImageToOss(file, is, imgKey);
        } catch (IOException e) {
            LogUtils.info(e.toString());
        }
        SkuCommentImg commentImg = skuCommentImgDao.insert(new SkuCommentImg(skuId, userId, imgKey));
        if (commentImg != null) {
            return ExecResult.succ(commentImg);
        }
        return ExecResult.fail("图片上传成功，但是记录失败，请重新上传");
    }

    @Override
    public List<Order> getAll(OrderStatus status, PageInfo pageInfo) {
        return orderDao.getAll(status, pageInfo);
    }

    @Override
    public List<Order> getAllForOrderList(OrderStatus status, PageInfo pageInfo) {
        List<Order> orderList = orderDao.getAll(status, pageInfo);
        return fillUserName(orderList);
    }

    @Override
    public Order getByOrderIdWithAllInfo(long orderId) {
        Order order = getByOrderId(orderId);
        if (order == null) {
            return null;
        }
        order.setGoodsList(orderGoodsDao.getByOrderId(orderId));
        order.setUserAddress(userAddressDao.getUserAddress(order.getAddressId()));
        order.setUserInfo(userInfoDao.getUserInfo(order.getUserId()));
        order.setOrderLogList(getOrderLogList(orderId));
        return order;
    }

    @Override
    public boolean clearOrderCache() {
        orderCache.invalidateAll();
        orderUserIdCache.invalidateAll();
        return true;
    }

    private List<OrderLog> getOrderLogList(long orderId) {
        List<OrderLog> orderLogList = orderLogDao.getLogListByOrderId(orderId);
        List<Long> adminUserIdList = orderLogExtractor.extractList(orderLogList);
        Map<Long, AdminUserInfo> adminUserInfoMap = new HashMap<Long, AdminUserInfo>();
        for (Long adminUserId : adminUserIdList) {
            AdminUserInfo adminUserInfo = accountService.getAdminUserInfoByUserId(adminUserId);
            if (adminUserInfo != null) {
                adminUserInfoMap.put(adminUserId, adminUserInfo);
            }
        }
        for (OrderLog orderLog : orderLogList) {
            orderLog.setUsername(adminUserInfoMap.get(orderLog.getUserId()).getRealName());
        }
        return orderLogList;
    }

    private List<Order> fillOrderGoods(List<Order> orderList) {
        List<Long> orderIdList = orderExtractor.extractList(orderList);
        List<OrderGoods> orderGoodsList = orderGoodsDao.getByOrderIdList(orderIdList);
        Map<Long, List<OrderGoods>> orderGoodsMap = orderGoodsExtractor.extractListMap(orderGoodsList);
        for (Order order : orderList) {
            order.setGoodsList(orderGoodsMap.get(order.getOrderId()));
        }
        return orderList;
    }

    private List<Order> fillUserName(List<Order> orderList) {
        List<Long> userIdList = userIdExtractor.extractList(orderList);
        List<UserInfo> userInfoList = userInfoDao.getUserInfo(userIdList);
        Map<Long, UserInfo> userInfoMap = userInfoExtractor.extractMap(userInfoList);
        for (Order order : orderList) {
            order.setUserInfo(userInfoMap.get(order.getUserId()));
        }
        return orderList;
    }

    private ExecInfo uploadImageToOss(MultipartFile file, InputStream is, String imgKey) {
        ExecInfo execInfo = ExecInfo.fail("上传云服务失败");
        try {
            execInfo = ossFormalApi.putObject(SystemConstants.BUCKET_NAME, imgKey, is, file.getSize());
        } catch (FileNotFoundException e) {
            LogUtils.info("上传图片文件未找到");
        }
        return execInfo;
    }

    private String getUserCommentImgKey(long skuId, long userId) {
        String imgKey = "images/sku/" + skuId + "/" + userId + "-" + System.currentTimeMillis();
        LogUtils.info("imgKey : " + imgKey);
        return imgKey;
    }

}
