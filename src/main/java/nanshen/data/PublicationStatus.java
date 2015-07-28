package nanshen.data;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * @author WANG Minghao
 */
public enum PublicationStatus {

    NEW("新添加"),

    OFFLINE("线下"),

    ONLINE("线上"),

    UNKNOWN("未知状态");

    private String desc;

    PublicationStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static PublicationStatus get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToArrivalStatus extends Castor<String, PublicationStatus> {
        @Override
        public PublicationStatus cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return PublicationStatus.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToArrivalStatus.class);
    }
}
