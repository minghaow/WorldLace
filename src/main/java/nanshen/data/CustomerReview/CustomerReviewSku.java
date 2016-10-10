package nanshen.data.CustomerReview;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * @author WANG Minghao
 */
@Table("CustomerReviewSku")
public class CustomerReviewSku {

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

    /** star */
    @Column
    private int star;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    public CustomerReviewSku() {
    }

    public CustomerReviewSku(long reviewId, long itemId, long skuId) {
        this.reviewId = reviewId;
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
