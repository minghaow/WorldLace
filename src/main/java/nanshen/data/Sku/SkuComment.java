package nanshen.data.Sku;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Sku Comment of finished user
 *
 * @author WANG Minghao
 */
@Table("SkuComment")
public class SkuComment {

    /**
     * ID
     */
    @Id
    private long id;

    /**
     * ID, {@link SkuDetail#id}
     */
    @Column
    private long skuId;

    /**
     * userId
     */
    @Column
    private long userId;

    /**
     * comment string
     */
    @Column
    private String comment;

    /**
     * star number
     */
    @Column
    private long star;

    /**
     * img count
     */
    @Column
    private long imgCount = 0;

    /**
     * create time for this look, will fill when create
     */
    @Column
    private Date createTime = new Date();

    public SkuComment() {
    }

    public SkuComment(String comment, long imgCount, long skuId, long star, long userId) {
        this.comment = comment;
        this.imgCount = imgCount;
        this.skuId = skuId;
        this.star = star;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public long getImgCount() {
        return imgCount;
    }

    public void setImgCount(long imgCount) {
        this.imgCount = imgCount;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
