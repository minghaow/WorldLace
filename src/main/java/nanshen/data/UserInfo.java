package nanshen.data;

import nanshen.utils.CollectionExtractor;
import org.nutz.dao.entity.annotation.*;

import java.util.*;

/**
 * User info data
 *
 * @author WANG Minghao
 */
@Table("UserInfo")
@TableIndexes(@Index(unique = true, name = "username", fields = {"username"}))
public class UserInfo {

    /** ID */
    @Id
    private long id;

    /** email */
    @Column
    private String email;

    /** hashed password */
    @Column
    @ColDefine(width = 150)
    private String password;

    /** user nick name */
    @Column
    private String username;

    /** phone, need to be verified */
    @Column
    private String phone;

    /** set for future use */
    @Column
    private int priority = 0;

    /** set for future use */
    @Column
    private long points = 0L;

    /** if unavailable, user cannot login */
    @Column
    private boolean available;

    /** if not activated, user cannot make payment */
    @Column
    private boolean activated = false;

    /** user type */
    @Column
    private UserType userType = UserType.AMATEUR;

    /** user login count */
    @Column
    private long loginCount;

    /** last login ip */
    @Column
    private String loginIp = "";

    /** last login time */
    @Column
    private Date loginTime = new Date();

    /** user authority list {@link #authorities} */
    @Many(target = UserAuthority.class, field = "userId")
    private List<UserAuthority> authoritiesInDb;

    /** 买手的权限列表 */
    private Set<AccessAuthority> authorities;

    /** 权限提取器 */
    private static final CollectionExtractor<AccessAuthority, UserAuthority> authorityExtractor =
            new CollectionExtractor<AccessAuthority, UserAuthority>() {
                @Override
                protected AccessAuthority convert(UserAuthority source) {
                    return source.getAuthority();
                }
            };

    public UserInfo(String username, String password) {
        this.username = username;
        this.phone = username;
        this.email = username;
        this.password = password;
        this.available = true;
        this.points = 0L;
        this.authorities = new HashSet<AccessAuthority>();
        this.authoritiesInDb = new ArrayList<UserAuthority>();
    }

    public UserInfo(String username) {
        this.username = username;
        this.available = true;
        this.authorities = new HashSet<AccessAuthority>();
        this.authoritiesInDb = new ArrayList<UserAuthority>();
    }

    /**
     * nutz使用的默认构造方法
     * <p />
     * <b>注意</b>：nutz使用，其他地方不应该使用
     */
    public UserInfo() {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public void setAuthorities(Set<AccessAuthority> authorities) {
        this.authorities = authorities;
    }

    public static CollectionExtractor<AccessAuthority, UserAuthority> getAuthorityExtractor() {
        return authorityExtractor;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get all user authority details
     *
     * @return
     */
    public Set<AccessAuthority> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    /**
     * get all user authority details
     *
     * @return 买手权限数据库结构列表
     */
    public List<UserAuthority> getAuthoritiesInDb() {
        return Collections.unmodifiableList(authoritiesInDb);
    }

    /**
     * nutz用于设置权限列表的方法
     * <p />
     * <b>注意</b>：nutz使用，其他地方不应该使用
     *
     * @param authoritiesInDb 买手权限数据库结构
     */
    public void setAuthoritiesInDb(List<UserAuthority> authoritiesInDb) {
        this.authoritiesInDb = authoritiesInDb;
        authorities = new HashSet<AccessAuthority>(authorityExtractor.extractList(authoritiesInDb));
    }

    public boolean hasAdminAuthority() {
        return authorities.contains(AccessAuthority.ADMIN);
    }

}
