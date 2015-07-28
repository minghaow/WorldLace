package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
@Table("LookInfo")
public class LookInfo {

    /** ID */
    @Id
    private long id;

    /** 用户名 */
    @Column
    private long uploadUserId;

    /** 搭配名称 */
    @Column
    private String title;

    /** 搭配子名称 */
    @Column
    private String subTitle;

    /** 描述, html页面 */
    @Column
    private String description;

    /** 标签 */
    @Column
    private String tags;

    /** 价格，单位：人民币分 */
    @Column
    private long price = 0L;

    /** 商品sku数量 */
    @Column
    private long skuCount = 0L;

    /** 图片数量 */
    @Column
    private long imgCount = 0L;

    /** 图片数量 */
    @Column
    private PublicationStatus status = PublicationStatus.NEW;

    /** 图片列表, 1-2 */
    private List<String> imgUrlList;

    /** 商品sku列表, 2 */
    private List<SkuInfo> skuInfoList;

    /** 标签列表 */
    private List<SkuTag> lookTagList;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    /** 更新时间 */
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

    public List<SkuTag> getLookTagList() {
        return lookTagList;
    }

    public void setLookTagList(List<SkuTag> lookTagList) {
        this.lookTagList = lookTagList;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }
}
