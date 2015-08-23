package nanshen.data;

import nanshen.utils.StringUtils;
import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * LookInfo
 * <br>
 * <strong>Note:</strong> Basic data for lanzhujue.com. Still need to optimize.
 *
 * @author WANG Minghao
 */
@Table("LookInfo")
public class LookInfo {

    /** ID */
    @Id
    private long id;

    /** the one who upload this look, updater will not update this value */
    @Column
    private long uploadUserId;

    /** title, attractive information */
    @Column
    private String title;

    /** sub title for the look, explaining the title normally */
    @Column
    private String subTitle;

    /** description, to be designed */
    @Column
    private String description;

    /** tags */
    @Column
    private String tags;

    /** price, price unit: RMB */
    @Column
    private long price = 0L;

    /** sku count, default 0 when this look create */
    @Column
    private long skuCount = 0L;

    /** image count, for displaying the image(the first one will be 0 and then 1, etc.) */
    @Column
    private long imgCount = 0L;

    /** online status {@code nanshen.data.PublicationStatus} */
    @Column
    private PublicationStatus status = PublicationStatus.NEW;

    /** image url list, default for 1 to 2 images */
    private List<String> imgUrlList;

    /** sku list, default for 1 to 2 skus */
    private List<SkuInfo> skuInfoList;

    /** tag id list */
    private String[] tagIdList;

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public LookInfo(Date createTime, String description, long imgCount, long price, String subTitle, String title,
                    Date updateTime, long uploadUserId) {
        this.createTime = createTime;
        this.description = description;
        this.imgCount = imgCount;
        this.price = price;
        this.subTitle = subTitle;
        this.title = title;
        this.updateTime = updateTime;
        this.uploadUserId = uploadUserId;
    }

    public LookInfo(long uploadUserId) {
        this.description = "";
        this.subTitle = "";
        this.title = "";
        this.uploadUserId = uploadUserId;
    }

    public LookInfo() {
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

    public long getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(long skuCount) {
        this.skuCount = skuCount;
    }

    public List<SkuInfo> getSkuInfoList() {
        return skuInfoList;
    }

    public void setSkuInfoList(List<SkuInfo> skuInfoList) {
        this.skuInfoList = skuInfoList;
    }

    public long getImgCount() {
        return imgCount;
    }

    public void setImgCount(long imgCount) {
        this.imgCount = imgCount;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String[] getTagIdList() {
        return StringUtils.getStringListFromString(tags, ",");
    }

    public void setTagIdList(String[] tagIdList) {
        this.tagIdList = tagIdList;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }
}
