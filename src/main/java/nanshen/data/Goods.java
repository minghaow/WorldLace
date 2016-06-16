package nanshen.data;

import nanshen.data.Sku.SkuDetail;
import nanshen.data.Sku.SkuTag;
import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Goods
 *
 * @author WANG Minghao
 */
@Table("Goods")
public class Goods {

    /** ID */
    @Id
    protected long id;

    /** skuId */
    @Column
    protected long skuId;

    /** the orderId */
    @Column
    protected long orderId = 0;

    /** the id of Cart */
    @Column
    protected long cartId = 0;

    /** the orderId of Cart */
    @Column
    protected long userId = 0;

    /** remark */
    @Column
    protected String remark = null;

    /** admin remark */
    @Column
    protected String adminRemark = null;

    /** title, attractive information */
    @Column
    protected String title;

    /** sub title for the sku, explaining the title normally */
    @Column
    protected String subTitle;

    /** warning for the sku */
    @Column
    protected String warning = null;

    /** price, price unit: RMB */
    @Column
    protected long price = 0;

    /** price, price unit: RMB */
    @Column
    protected long originPrice = 0;

    /** price, price unit: RMB */
    @Column
    protected long count = 0;

    /** discount price, price unit: RMB */
    @Column
    protected long discountPrice = 0;

    /** discount code */
    @Column
    protected String discountCode = null;

    /** image url list, default for 1 to 2 images */
    protected List<String> imgUrlList;

    /** sku list, default for 1 to 2 skus */
    protected List<SkuTag> skuTagList;

    /** sku detail */
    protected List<SkuDetail> skuDetailList;

    /** create time for this look, will fill when create */
    @Column
    protected Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    protected Date updateTime = new Date();

    /** is cart goods */
    protected boolean isCartGoods = false;

    @Column
    /** item id, which is equal to id */
    protected long itemId = 0;

    @Column
    /** option 1 name */
    protected String option1 = "";

    @Column
    /** option 2 name */
    protected String option2 = "";

    public Goods(String adminRemark, long cartId, long count, Date createTime, String discountCode, long discountPrice,
                  long id, List<String> imgUrlList, long orderId, long originPrice, long price, String remark,
                  List<SkuDetail> skuDetailList, List<SkuTag> skuTagList, PublicationStatus status, String subTitle,
                  String title, Date updateTime, long userId, String warning) {
        this.adminRemark = adminRemark;
        this.cartId = cartId;
        this.count = count;
        this.createTime = createTime;
        this.discountCode = discountCode;
        this.discountPrice = discountPrice;
        this.id = id;
        this.imgUrlList = imgUrlList;
        this.orderId = orderId;
        this.originPrice = originPrice;
        this.price = price;
        this.remark = remark;
        this.skuDetailList = skuDetailList;
        this.skuTagList = skuTagList;
        this.subTitle = subTitle;
        this.title = title;
        this.updateTime = updateTime;
        this.userId = userId;
        this.warning = warning;
    }

    public Goods(long cartId, long count, String discountCode, long discountPrice, long originPrice, long price,
                 String remark, String subTitle, String title, long userId, long skuId) {
        this.cartId = cartId;
        this.count = count;
        this.discountCode = discountCode;
        this.discountPrice = discountPrice;
        this.originPrice = originPrice;
        this.price = price;
        this.remark = remark;
        this.subTitle = subTitle;
        this.title = title;
        this.userId = userId;
        this.skuId = skuId;
    }

    public Goods() {
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getConvertedDiscountPrice() {
        return ViewUtils.priceConverter(discountPrice);
    }

    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(long originPrice) {
        this.originPrice = originPrice;
    }

    public long getPrice() {
        return price;
    }

    public String getConvertedPrice() {
        return ViewUtils.priceConverter(price);
    }

    public String getConvertedPriceNo() {
        return ViewUtils.priceConverterNo(price);
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
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

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public boolean isCartGoods() {
        return isCartGoods;
    }

    public void setIsCartGoods(boolean isCartGoods) {
        this.isCartGoods = isCartGoods;
    }

    public String getConvertedTotalPrice() {
        return ViewUtils.priceConverter(price * count);
    }

    public String getConvertedTotalPriceNo() {
        return ViewUtils.priceConverterNo(price * count);
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }
}
