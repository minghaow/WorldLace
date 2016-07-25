package nanshen.data.CustomerReview;

import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WANG Minghao
 */
@Table("CustomerReview")
public class CustomerReview {

    /** ID */
    @Id
    private long id;

    /** 用户ID */
    @Column
    private long userId;

    /** 用户名 */
    @Column
    private String username = "";

    /** 喜欢数目 */
    @Column
    private long likeCnt = 0;

    /** 查看数目 */
    @Column
    private long viewCnt = 0;

    /** 商品评星 */
    @Column
    private long skuStar = 0;

    /** 物流评星 */
    @Column
    private long shippingStar = 0;

    /** 标题 */
    @Column
    private String title = "";

    /** 是否已经发布 */
    @Column
    private boolean isPublished = false;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    /** 更新时间 */
    @Column
    private Date updateTime = new Date();

    private List<CustomerReviewSku> customerReviewSkuList = new ArrayList<CustomerReviewSku>();

    private List<CustomerReviewImg> customerReviewImgList = new ArrayList<CustomerReviewImg>();

    private CustomerReviewDetail customerReviewDetail = null;

    public CustomerReview() {
    }

    public CustomerReview(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public CustomerReview(String title, long userId, String username, long skuStar, long shippingStar) {
        this.title = title;
        this.userId = userId;
        this.username = username;
        this.skuStar = skuStar;
        this.shippingStar = shippingStar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getConvertedCreateTime() {
        return ViewUtils.convertDateToStringWithoutTime(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(long likeCnt) {
        this.likeCnt = likeCnt;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public CustomerReviewDetail getCustomerReviewDetail() {
        return customerReviewDetail;
    }

    public void setCustomerReviewDetail(CustomerReviewDetail customerReviewDetail) {
        this.customerReviewDetail = customerReviewDetail;
    }

    public List<CustomerReviewImg> getCustomerReviewImgList() {
        return customerReviewImgList;
    }

    public void setCustomerReviewImgList(List<CustomerReviewImg> customerReviewImgList) {
        this.customerReviewImgList = customerReviewImgList;
    }

    public List<CustomerReviewSku> getCustomerReviewSkuList() {
        return customerReviewSkuList;
    }

    public void setCustomerReviewSkuList(List<CustomerReviewSku> customerReviewSkuList) {
        this.customerReviewSkuList = customerReviewSkuList;
    }

    public long getShippingStar() {
        return shippingStar;
    }

    public void setShippingStar(long shippingStar) {
        this.shippingStar = shippingStar;
    }

    public long getSkuStar() {
        return skuStar;
    }

    public void setSkuStar(long skuStar) {
        this.skuStar = skuStar;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }
}
