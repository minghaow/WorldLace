package nanshen.data;

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
    private long id;

    /** skuId */
    @Column
    private long skuId;

    /** the orderId */
    @Column
    private long orderId = 0;

    /** the id of Cart */
    @Column
    private long cartId = 0;

    /** the orderId of Cart */
    @Column
    private long userId = 0;

    /** remark */
    @Column
    private String remark = null;

    /** admin remark */
    @Column
    private String adminRemark = null;

    /** title, attractive information */
    @Column
    private String title;

    /** sub title for the sku, explaining the title normally */
    @Column
    private String subTitle;

    /** warning for the sku */
    @Column
    private String warning = null;

    /** price, price unit: RMB */
    @Column
    private long price = 0;

    /** price, price unit: RMB */
    @Column
    private long originPrice = 0;

    /** price, price unit: RMB */
    @Column
    private long count = 0;

    /** discount price, price unit: RMB */
    @Column
    private long discountPrice = 0;

    /** discount code */
    @Column
    private String discountCode = null;

    /** online status {@code nanshen.data.PublicationStatus} */
    @Column
    private PublicationStatus status = PublicationStatus.NEW;

    /** image url list, default for 1 to 2 images */
    private List<String> imgUrlList;

    /** sku list, default for 1 to 2 skus */
    private List<SkuTag> skuTagList;

    /** sku detail */
    private List<SkuDetail> skuDetailList;

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    private Date updateTime = new Date();

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
        this.status = status;
        this.subTitle = subTitle;
        this.title = title;
        this.updateTime = updateTime;
        this.userId = userId;
        this.warning = warning;
    }

    public Goods(long cartId, long count, String discountCode, long discountPrice, long originPrice, long price,
                 String remark, String subTitle, String title, long userId) {
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

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
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
}
