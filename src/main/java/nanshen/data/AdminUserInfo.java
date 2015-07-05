package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * @author WANG Minghao
 */
@Table("AdminUserInfo")
public class AdminUserInfo {

    /** ID */
    @Id
    private long id;

    /** 用户名 */
    @Column
    private String username;

    /** 添加时间 */
    @Column
    private Date createTime = new Date();

    public AdminUserInfo(Date createTime, String username) {
        this.createTime = createTime;
        this.username = username;
    }

    public AdminUserInfo(String username) {
        this.username = username;
    }

    public AdminUserInfo() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
