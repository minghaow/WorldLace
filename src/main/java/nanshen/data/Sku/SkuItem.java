package nanshen.data.Sku;

import nanshen.data.CustomerReview.CustomerReview;
import nanshen.data.PublicationStatus;
import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SkuItem, item
 *
 * @author WANG Minghao
 */
@Table("SkuItem")
public class SkuItem {

    /** ID */
    @Id
    private long id;

    /** the one who upload this look, updater will not update this value */
    @Column
    private long uploadUserId = 0;

    /** title, attractive information */
    @Column
    private String title = "";

    /** sub title for the sku, explaining the title normally */
    @Column
    private String subTitle = "";

    /** order notice for the sku */
    @Column
    private String notice = "";

    /** warning for the sku */
    @Column
    private String warning = "";

    /** sku link for outside domains */
    @Column
    private String url = "";

    /** description, to be designed */
    @Column
    private String description = "";

    /** tags */
    @Column
    private String tags = "";

    /** relateItem */
    @Column
    private String relateItem = "";

    /** price, price unit: RMB */
    @Column
    private long price = 0;

    /** price, price unit: RMB */
    @Column
    private long originPrice = 0;

    /** image count, for displaying the image(the first one will be 0 and then 1, etc.) */
    @Column
    private long imgCount = 0L;

    /** description image count, for displaying the image(the first one will be 0 and then 1, etc.) */
    @Column
    private long contentImgCount = 0L;

    /** online status {@code nanshen.data.PublicationStatus} */
    @Column
    private PublicationStatus status = PublicationStatus.ONLINE;

    /** is gift wrap valid */
    @Column
    private boolean giftWrapValid = false;

    @Column
    private StoreType storeType = StoreType.ITLACE;

    /** is gift wrap valid */
    @Column
    private boolean hasGif = false;

    /** 0 for no free shipping, 1 for mainly free shipping, 2 for all */
    @Column
    private long freeShippingLevel = 0;

    /** is this product only selling in taoyuan */
    @Column
    private boolean onlyInTaoyuan = false;

    /** image url list, default for 1 to 2 images */
    private List<String> imgUrlList;

    /** sku list, default for 1 to 2 skus */
    private List<SkuTag> skuTagList;

    /** sku detail */
    private List<SkuDetail> skuDetailList;

    /** sales info for skuDetails */
    private List<SalesInfo> salesInfos;

    /** total sales info for sku items */
    private long totalSalesInfo = 0;

    /** monthly sales info for sku items */
    private long monthlySalesInfo = 0;

    /** customer review */
    private List<CustomerReview> customerReviewList;

    /** sku item describe info */
    private SkuItemDescription skuItemDescription;

    private List<SkuItem> relateSkuItemList = new ArrayList<SkuItem>();

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public SkuItem(Date createTime, String description, long id, long imgCount, List<String> imgUrlList, long price,
                   List<SkuTag> skuTagList, PublicationStatus status, String subTitle, String tags, String title,
                   Date updateTime, long uploadUserId, String url, String warning, String notice) {
        this.createTime = createTime;
        this.description = description;
        this.id = id;
        this.imgCount = imgCount;
        this.imgUrlList = imgUrlList;
        this.price = price;
        this.skuTagList = skuTagList;
        this.status = status;
        this.subTitle = subTitle;
        this.tags = tags;
        this.title = title;
        this.updateTime = updateTime;
        this.uploadUserId = uploadUserId;
        this.url = url;
        this.warning = warning;
        this.notice = notice;
    }

    public SkuItem() {
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getImgCount() {
        return imgCount;
    }

    public void setImgCount(long imgCount) {
        this.imgCount = imgCount;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public long getPrice() {
        return price;
    }

    public String getDisplayPrice() {
        return ViewUtils.priceConverter(price);
    }

    public void setPrice(long price) {
        this.price = price;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public List<SkuDetail> getSkuDetailList() {
        return skuDetailList;
    }

    public void setSkuDetailList(List<SkuDetail> skuDetailList) {
        this.skuDetailList = skuDetailList;
    }

    public long getContentImgCount() {
        return contentImgCount;
    }

    public void setContentImgCount(long contentImgCount) {
        this.contentImgCount = contentImgCount;
    }

    public long getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(long originPrice) {
        this.originPrice = originPrice;
    }

    public String getDisplayOriginPrice() {
        return ViewUtils.priceConverter(originPrice);
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<CustomerReview> getCustomerReviewList() {
        return customerReviewList;
    }

    public void setCustomerReviewList(List<CustomerReview> customerReviewList) {
        this.customerReviewList = customerReviewList;
    }

    public List<SalesInfo> getSalesInfos() {
        return salesInfos;
    }

    public void setSalesInfos(List<SalesInfo> salesInfos) {
        this.salesInfos = salesInfos;
    }

    public long getTotalSalesInfo() {
        return totalSalesInfo;
    }

    public void setTotalSalesInfo(long totalSalesInfo) {
        this.totalSalesInfo = totalSalesInfo;
    }

    public long getMonthlySalesInfo() {
        return monthlySalesInfo;
    }

    public void setMonthlySalesInfo(long monthlySalesInfo) {
        this.monthlySalesInfo = monthlySalesInfo;
    }

    public SkuItemDescription getSkuItemDescription() {
        return skuItemDescription;
    }

    public void setSkuItemDescription(SkuItemDescription skuItemDescription) {
        this.skuItemDescription = skuItemDescription;
    }

    public long getFreeShippingLevel() {
        return freeShippingLevel;
    }

    public void setFreeShippingLevel(long freeShippingLevel) {
        this.freeShippingLevel = freeShippingLevel;
    }

    public boolean isGiftWrapValid() {
        return giftWrapValid;
    }

    public void setGiftWrapValid(boolean giftWrapValid) {
        this.giftWrapValid = giftWrapValid;
    }

    public boolean isOnlyInTaoyuan() {
        return onlyInTaoyuan;
    }

    public void setOnlyInTaoyuan(boolean onlyInTaoyuan) {
        this.onlyInTaoyuan = onlyInTaoyuan;
    }

    public String getRelateItem() {
        return relateItem;
    }

    public void setRelateItem(String relateItem) {
        this.relateItem = relateItem;
    }

    public List<SkuItem> getRelateSkuItemList() {
        return relateSkuItemList;
    }

    public void setRelateSkuItemList(List<SkuItem> relateSkuItemList) {
        this.relateSkuItemList = relateSkuItemList;
    }

    public boolean isHasGif() {
        return hasGif;
    }

    public void setHasGif(boolean hasGif) {
        this.hasGif = hasGif;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }
}
