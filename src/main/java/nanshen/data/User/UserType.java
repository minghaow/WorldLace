package nanshen.data.User;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

import java.util.Arrays;
import java.util.List;

/**
 * User type
 *
 * @author WANG Minghao
 */
public enum UserType {

    PRO("职业买手"),

    AMATEUR("普通用户"),

    UNKNOWN("未知");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserType get(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToUserType extends Castor<String, UserType> {

        @Override
        public UserType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return UserType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToUserType.class);
    }

    public static List<UserType> getAvailableTypeForHoldWithPoints() {
        return Arrays.asList(AMATEUR);
    }

    public static List<UserType> getAvailableTypeForHold() {
        return Arrays.asList(PRO);
    }

}