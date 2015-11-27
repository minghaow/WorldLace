package nanshen.data;

import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * SkuInfo
 *
 * @author WANG Minghao
 */
@Table("SkuInfo")
public class SkuInfo {

    /** ID */
    @Id
    private long id;

    /** look ID */
    @Column
    private long lookId = 0;

    /** the one who upload this look, updater will not update this value */
    @Column
    private long uploadUserId = 0;

    /** title, attractive information */
    @Column
    private String title;

    /** sub title for the look, explaining the title normally */
    @Column
    private String subTitle;

    /** sku link for outside domains */
    @Column
    private String url;

    /** sku detail type that uploader decides */
    @Column
    private SkuDetailType detailType;

    /** description, to be designed */
    @Column
    private String description;

    /** tags */
    @Column
    private String tags;

    /** price, price unit: RMB */
    @Column
    private long price;

    /** image count, for displaying the image(the first one will be 0 and then 1, etc.) */
    @Column
    private long imgCount = 0L;

    /** online status {@code nanshen.data.PublicationStatus} */
    @Column
    private PublicationStatus status = PublicationStatus.NEW;

    /** image url list, default for 1 to 2 images */
    private List<String> imgUrlList;

    /** sku list, default for 1 to 2 skus */
    private List<SkuTag> skuTagList;

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public SkuInfo(Date createTime, String description, long price, String subTitle, String title, String url, Date
            updateTime, long uploadUserId, String tags) {
        this.createTime = createTime;
        this.description = description;
        this.price = price;
        this.subTitle = subTitle;
        this.title = title;
        this.url = url;
        this.updateTime = updateTime;
        this.uploadUserId = uploadUserId;
        this.tags = tags;
    }

    public SkuInfo(long uploadUserId) {
        this.description = "";
        this.subTitle = "";
        this.title = "";
        this.uploadUserId = uploadUserId;
    }

    public SkuInfo() {
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getConvertedCreateTime() {
        return ViewUtils.convertDateToString(createTime);
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

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public long getPrice() {
        return price;
    }

    public String getViewPrice() {
        return ViewUtils.priceConverter(price);
    }

    public void setPrice(long price) {
        this.price = price;
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

    public long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getImgCount() {
        return imgCount;
    }

    public void setImgCount(long imgCount) {
        this.imgCount = imgCount;
    }

    public List<SkuTag> getSkuTagList() {
        return skuTagList;
    }

    public void setSkuTagList(List<SkuTag> skuTagList) {
        this.skuTagList = skuTagList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLookId() {
        return lookId;
    }

    public void setLookId(long lookId) {
        this.lookId = lookId;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public SkuDetailType getDetailType() {
        return detailType;
    }

    public void setDetailType(SkuDetailType detailType) {
        this.detailType = detailType;
    }
}
