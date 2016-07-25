package nanshen.data.CustomerReview;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author WANG Minghao
 */
@Table("CustomerReviewDetail")
public class CustomerReviewDetail {

    /** ID */
    @Id
    private long id;

    /** review ID */
    @Column
    private long reviewId;

    /** 内容 */
    @Column
    private String content = "";

    public CustomerReviewDetail() {
    }

    public CustomerReviewDetail(String content, long reviewId) {
        this.content = content;
        this.reviewId = reviewId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
