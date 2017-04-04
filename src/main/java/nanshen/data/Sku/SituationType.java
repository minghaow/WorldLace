package nanshen.data.Sku;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SituationType
 *
 * @author WANG Minghao
 */
public enum SituationType {

    WORK("go to work"),

    CONFERENCE("attend a conference"),

    SPORTS("have some sports"),

    NIGHT("ladies' night"),

    PARTY("party Friday"),

    DATING("dating out"),

    HOME("stay at home"),

    UNKNOWN("unknown");

    private String desc;

    SituationType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static SituationType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSituationType extends Castor<String, SituationType> {
        @Override
        public SituationType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SituationType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSituationType.class);
    }
}
