package nanshen.data;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
 * 买手权限数据表
 *
 * @author WANG Minghao
 */
@Table("UserAuthority")
public class UserAuthority {

    /** 数据库主键，权限ID */
    @Id
    private long id;

    /** 买手ID */
    @Column
    private long buyerId;

    /** 添加该权限的人的ID */
    @Column
    private long adderId;

    /** 权限添加的时间 */
    @Column
    @ColDefine(type = ColType.DATETIME)
    private Date addTime;

    /** 权限值 */
    @Column
    @ColDefine(type = ColType.VARCHAR)
    private AccessAuthority authority;

    public UserAuthority(long adderId, Date addTime, AccessAuthority authority, long buyerId) {
        this.adderId = adderId;
        this.addTime = addTime;
        this.authority = authority;
        this.buyerId = buyerId;
    }

    public UserAuthority() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getAdderId() {
        return adderId;
    }

    public void setAdderId(long adderId) {
        this.adderId = adderId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public AccessAuthority getAuthority() {
        return authority;
    }

    /**
     * nutz用于设置权限值的方法
     * <p />
     * <b>注意</b>：nutz使用，其他地方不应该使用
     *
     * @param authority
     */
    public void setAuthority(String authority) {
        this.authority = AccessAuthority.get(authority);
    }

}
