package nanshen.data.CustomerReview;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * @author WANG Minghao
 */
@Table("CustomerReviewImg")
public class CustomerReviewImg {

    /** ID */
    @Id
    private long id;

    /** review ID */
    @Column
    private long reviewId;

    /** item ID */
    @Column
    private long itemId;

    /** sku ID */
    @Column
    private long skuId;

    /** user ID */
    @Column
    private long userId;

    /** 图片 */
    @Column
    private String imgKey;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    public CustomerReviewImg() {
    }

    public CustomerReviewImg(String imgKey, long reviewId, long userId, long itemId, long skuId) {
        this.imgKey = imgKey;
        this.reviewId = reviewId;
        this.userId = userId;
        this.skuId = skuId;
        this.itemId = itemId;
    }

    public Date getCreateTime() {
        return createTime;
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

    public String getImgKey() {
        return imgKey;
    }

    public void setImgKey(String imgKey) {
        this.imgKey = imgKey;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
