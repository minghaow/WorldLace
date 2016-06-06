package nanshen.data;

import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Order
 *
 * @author WANG Minghao
 */
@Table("taoyuan.Order")
public class Order {

    /** ID */
    @Id
    private long orderId;

    /** the orderId for user */
    @Column
    private long showOrderId = 0;

    /** the one who create this order */
    @Column
    private long userId = 0;

    /** the address id of the order, address may changed, so copy one piece for each order */
    @Column
    private long addressId = 0;

    /** user remark for the order */
    @Column
    private String remark;

    /** admin remark for the order */
    @Column
    private String adminRemark;

    /** total price, price unit: RMB */
    @Column
    private long totalPrice;

    /** goods price, price unit: RMB */
    @Column
    private long goodsPrice;

    /** goodsCount */
    @Column
    private long goodsCount;

    /** shippingPrice */
    @Column
    private long shippingPrice;

    /** taxPrice */
    @Column
    private long taxPrice;

    /** discount */
    @Column
    private long discountPrice;

    /** discountCode */
    @Column
    private String discountCode;

    /** tags */
    @Column
    private String tags;

    /** tradeNo */
    @Column
    private String tradeNo;

    /** online status {@code nanshen.data.OrderStatus} */
    @Column
    private OrderStatus orderStatus = OrderStatus.NEW;

    /** online status {@code nanshen.data.TransportStatus} */
    @Column
    private TransportStatus transportStatus = TransportStatus.NEW;

    /** sku list, default for 1 to 2 skus */
    private List<SkuTag> skuTagList;

    /** sku detail */
    private List<SkuDetail> skuDetailList;

    /** create time for this order, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this order, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    /** update time for this order, all operator will update this value */
    @Column
    private Date finishTime = new Date();

    /** goods list */
    private List<OrderGoods> goodsList = new ArrayList<OrderGoods>();

    public Order() {
    }

    public Order(Cart cart) {
        this.transportStatus = TransportStatus.NEW;
        this.orderStatus = OrderStatus.NEW;
        this.userId = cart.getUserId();
        this.remark = cart.getRemark();
        this.shippingPrice = cart.getShippingPrice();
        this.showOrderId = generateShowOrderId(System.currentTimeMillis(), cart.getUserId());
        this.skuDetailList = cart.getSkuDetailList();
        this.tags = cart.getTags();
        this.taxPrice = 0;
        this.totalPrice = 0;
        this.adminRemark = cart.getAdminRemark();
        this.discountCode = cart.getDiscountCode();
        this.discountPrice = 0;
        this.goodsCount = 0;
        this.createTime = new Date();
        this.updateTime = new Date();
        this.finishTime = new Date();
    }

    public Order(String adminRemark, Date createTime, String discountCode, long discountPrice, Date finishTime,
                 long goodsCount, long goodsPrice, long orderId, OrderStatus orderStatus, String remark, long addressId,
                 long shippingPrice, long showOrderId, List<SkuDetail> skuDetailList, List<SkuTag> skuTagList,
                 String tags, long taxPrice, long totalPrice, TransportStatus transportStatus, Date updateTime, long userId) {
        this.adminRemark = adminRemark;
        this.createTime = createTime;
        this.discountCode = discountCode;
        this.discountPrice = discountPrice;
        this.finishTime = finishTime;
        this.goodsCount = goodsCount;
        this.goodsPrice = goodsPrice;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.remark = remark;
        this.shippingPrice = shippingPrice;
        this.showOrderId = showOrderId;
        this.skuDetailList = skuDetailList;
        this.skuTagList = skuTagList;
        this.tags = tags;
        this.taxPrice = taxPrice;
        this.totalPrice = totalPrice;
        this.transportStatus = transportStatus;
        this.updateTime = updateTime;
        this.userId = userId;
        this.addressId = addressId;
    }

    private long generateShowOrderId(long timeMillis, long userId) {
        if(userId<=0 || timeMillis<=0) {
            return 0;
        }
        long showOrderId = timeMillis + userId * 1000001;
        char[] c = String.valueOf(showOrderId).toCharArray();
        for(int i = 0; i < c.length; i++) {
            int number = c[i] - '0';
            number = (number + 3) % 10;
            c[i] = (char) ('0' + number);
        }
        String showOrderIdStr = new String(c);
        return Long.parseLong(showOrderIdStr);
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeString() {
        return ViewUtils.convertDateToString(createTime);
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public long getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(long goodsCount) {
        this.goodsCount = goodsCount;
    }

    public long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(long shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public List<SkuDetail> getSkuDetailList() {
        return skuDetailList;
    }

    public void setSkuDetailList(List<SkuDetail> skuDetailList) {
        this.skuDetailList = skuDetailList;
    }

    public List<SkuTag> getSkuTagList() {
        return skuTagList;
    }

    public void setSkuTagList(List<SkuTag> skuTagList) {
        this.skuTagList = skuTagList;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(long taxPrice) {
        this.taxPrice = taxPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public String getTotalPriceString() {
        return ViewUtils.priceConverterNo(totalPrice);
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getUpdateTimeString() {
        return ViewUtils.convertDateToString(updateTime);
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinishTimeString() {
        return ViewUtils.convertDateToString(finishTime);
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getShowOrderId() {
        return showOrderId;
    }

    public void setShowOrderId(long showOrderId) {
        this.showOrderId = showOrderId;
    }

    public TransportStatus getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(TransportStatus transportStatus) {
        this.transportStatus = transportStatus;
    }

    public List<OrderGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
