package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * ContactMsg
 *
 * @author WANG Minghao
 */
@Table("ContactMsg")
public class ContactMsg {

    /** ID */
    @Id
    private long id;

    /** the one who create this order */
    @Column
    private long userId = 0;

    /** user name */
    @Column
    private String name = "";

    /** user email to contact back */
    @Column
    private String email = "";

    /** main content */
    @Column
    private String content = "";

    /** create time for this order, will fill when create */
    @Column
    private Date createTime = new Date();

    public ContactMsg() {
    }

    public ContactMsg(String content, Date createTime, String email, String name, long userId) {
        this.content = content;
        this.createTime = createTime;
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public ContactMsg(String content, Date createTime, String email, long id, String name, long userId) {
        this.content = content;
        this.createTime = createTime;
        this.email = email;
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
