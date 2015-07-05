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
public class SkuInfo {

    /** ID */
    @Id
    private long id;

    /** 用户名 */
    @Column
    private long uploadUserId;

    /** 用户名 */
    @Column
    private String uploadUserName;

    /** 搭配名称 */
    @Column
    private String title;

    /** 搭配子名称 */
    @Column
    private String subTitle;

    /** 描述 */
    @Column
    private String desc;

    /** 价格，单位：人民币分 */
    @Column
    private long price;

    /** 图片列表 */
    @Column
    private List<String> imgUrlList;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    /** 更新时间 */
    @Column
    private Date updateTime = new Date();

    public SkuInfo(Date createTime, String desc, List<String> imgUrlList, long price, String subTitle, String title, Date updateTime, long uploadUserId, String uploadUserName) {
        this.createTime = createTime;
        this.desc = desc;
        this.imgUrlList = imgUrlList;
        this.price = price;
        this.subTitle = subTitle;
        this.title = title;
        this.updateTime = updateTime;
        this.uploadUserId = uploadUserId;
        this.uploadUserName = uploadUserName;
    }

    public SkuInfo() {
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }
}
