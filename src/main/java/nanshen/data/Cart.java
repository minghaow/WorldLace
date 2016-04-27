package nanshen.data;

import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cart
 *
 * @author WANG Minghao
 */
@Table("Cart")
public class Cart {

    /** ID */
    @Id
    private long id;

    /** the one who create this order */
    @Column
    private long userId = 0;

    /** user remark for the order */
    @Column
    private String remark;

    /** admin remark for the order */
    @Column
    private String adminRemark;

    /** total price, price unit: RMB */
    @Column
    private long totalPrice = 0;

    /** goods price, price unit: RMB */
    @Column
    private long goodsPrice = 0;

    /** goodsCount */
    @Column
    private long goodsCount = 0;

    /** shippingPrice */
    @Column
    private long shippingPrice = 0;

    /** tax */
    @Column
    private long taxPrice = 0;

    /** discount */
    @Column
    private long discountPrice = 0;

    /** discountCode */
    @Column
    private long discountCode;

    /** tags */
    @Column
    private String tags;

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
    private List<Goods> goodsList = new ArrayList<Goods>();

    public Cart() {
    }

    public Cart(long userId) {
        this.userId = userId;
    }

    public Cart(String adminRemark, Date createTime, long discountCode, long discountPrice, Date finishTime,
                long goodsCount, long goodsPrice, long id, OrderStatus orderStatus, String remark,
                long shippingPrice, List<SkuDetail> skuDetailList, List<SkuTag> skuTagList,
                String tags, long taxPrice, long totalPrice, TransportStatus transportStatus, Date updateTime, long userId) {
        this.adminRemark = adminRemark;
        this.createTime = createTime;
        this.discountCode = discountCode;
        this.discountPrice = discountPrice;
        this.finishTime = finishTime;
        this.goodsCount = goodsCount;
        this.goodsPrice = goodsPrice;
        this.id = id;
        this.orderStatus = orderStatus;
        this.remark = remark;
        this.shippingPrice = shippingPrice;
        this.skuDetailList = skuDetailList;
        this.skuTagList = skuTagList;
        this.tags = tags;
        this.taxPrice = taxPrice;
        this.totalPrice = totalPrice;
        this.transportStatus = transportStatus;
        this.updateTime = updateTime;
        this.userId = userId;
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

    public long getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(long discountCode) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public TransportStatus getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(TransportStatus transportStatus) {
        this.transportStatus = transportStatus;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
