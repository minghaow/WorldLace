package nanshen.data.User;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

/**
 * 访问权限的定义
 * <p />
 * <b>注意</b>：基于SpringSecurity的默认设置，权限必须以“ROLE_”开头，因此{@link #authority}字段必须以“ROLE_”开头
 *
 * @author WANG Minghao
 */
public enum AccessAuthority implements GrantedAuthority {

    /** 每个账号都有的默认权限 */
    LOGIN(AccessAuthority.AUTHORITY_LOGIN, "基本权限"),

    /** 每个买手都有的默认权限 */
    BUYER(AccessAuthority.AUTHORITY_BUYER, "买手权限"),

    BUYER_SETTLE(AccessAuthority.AUTHORITY_BUYER_SETTLE, "买手结算权限"),

    /** 每个管理员都有的默认权限 */
    ADMIN(AccessAuthority.AUTHORITY_ADMIN, "基本管理员权限"),

    ADMIN_LIST(AccessAuthority.AUTHORITY_ADMIN_LIST, "管理员全部订单列表操作权限"),

    ADMIN_AUDIT(AccessAuthority.AUTHORITY_ADMIN_AUDIT, "管理员审核权限"),

    ADMIN_CHECK(AccessAuthority.AUTHORITY_ADMIN_CHECK, "管理员对账权限"),

    ADMIN_TRANSPORT(AccessAuthority.AUTHORITY_ADMIN_TRANSPORT, "管理员转运流程控制权限"),

    ADMIN_SMS(AccessAuthority.AUTHORITY_ADMIN_SMS, "管理员发送短信权限"),

    ADMIN_RECRUIT(AccessAuthority.AUTHORITY_ADMIN_RECRUIT, "管理员招聘买手权限"),

    ADMIN_TEST(AccessAuthority.AUTHORITY_ADMIN_TEST, "管理员测试接口权限"),

    /** 用于兼容无法识别的权限，当数据库中出现新的权限值时，旧系统会将其识别为该值而不会抛Exception */
    UNKNOWN(AccessAuthority.AUTHORITY_UNKNOWN, "未定义");

    public static final String AUTHORITY_LOGIN = "ROLE_LOGIN";

    public static final String AUTHORITY_BUYER = "ROLE_BUYER";

    public static final String AUTHORITY_BUYER_SETTLE = "ROLE_BUYER_SETTLE";

    public static final String AUTHORITY_ADMIN = "ROLE_ADMIN";

    public static final String AUTHORITY_ADMIN_LIST = "ROLE_ADMIN_LIST";

    public static final String AUTHORITY_ADMIN_AUDIT = "ROLE_ADMIN_AUDIT";

    public static final String AUTHORITY_ADMIN_CHECK = "ROLE_ADMIN_CHECK";

    public static final String AUTHORITY_ADMIN_TRANSPORT = "ROLE_ADMIN_TRANSPORT";

    public static final String AUTHORITY_ADMIN_SMS = "ROLE_ADMIN_SMS";

    public static final String AUTHORITY_ADMIN_RECRUIT = "ROLE_ADMIN_RECRUIT";

    public static final String AUTHORITY_ADMIN_TEST = "ROLE_ADMIN_TEST";

    public static final String AUTHORITY_UNKNOWN = "ROLE_UNKNOWN";

    public static final Map<String, AccessAuthority> AUTHORITY_MAP = new HashMap<String, AccessAuthority>();

    static {
        for (AccessAuthority accessAuthority : values()) {
            AUTHORITY_MAP.put(accessAuthority.getAuthority(), accessAuthority);
        }
    }

    /** 用于数据库存储和SpringSecurity权限判断，该值必须唯一 */
    private String authority;

    private String desc;

    AccessAuthority(String authority, String desc) {
        this.authority = authority;
        this.desc = desc;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return authority;
    }

    public static AccessAuthority get(String authority) {
        try {
            return AUTHORITY_MAP.get(authority);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToAccessAuthority extends Castor<String, AccessAuthority> {

        @Override
        public AccessAuthority cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return AccessAuthority.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToAccessAuthority.class);
    }

}
