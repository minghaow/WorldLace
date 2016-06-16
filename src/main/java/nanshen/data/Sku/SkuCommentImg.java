package nanshen.data.Sku;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Sku Comment Img of finished user
 *
 * @author WANG Minghao
 */
@Table("SkuCommentImg")
public class SkuCommentImg {

    /**
     * ID
     */
    @Id
    private long id;

    /**
     * ID, {@link SkuComment#id}
     */
    @Column
    private long commentId;

    /**
     * userId
     */
    @Column
    private long userId;

    /**
     * skuId
     */
    @Column
    private long skuId;

    /**
     * star number
     */
    @Column
    private String imgUrl;

    /**
     * create time for this look, will fill when create
     */
    @Column
    private Date createTime = new Date();

    public SkuCommentImg() {
    }

    public SkuCommentImg(long skuId, long userId, String imgUrl) {
        this.imgUrl = imgUrl;
        this.skuId = skuId;
        this.userId = userId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }
}
