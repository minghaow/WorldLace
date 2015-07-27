package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * @author WANG Minghao
 */
@Table("LookTag")
public class LookTag {

    /** ID */
    @Id
    private long id;

    /** 搭配名称 */
    @Column
    private String tagName;

    /** 描述, html页面 */
    @Column
    private String description;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    /** 更新时间 */
    @Column
    private Date updateTime = new Date();

    public LookTag(Date createTime, String description, String tagName, Date updateTime) {
        this.createTime = createTime;
        this.tagName = tagName;
        this.description = description;
        this.updateTime = updateTime;
    }

    public LookTag() {
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
