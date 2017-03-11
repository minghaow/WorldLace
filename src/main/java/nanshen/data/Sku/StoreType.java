package nanshen.data.Sku;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * StringToStoreType
 *
 * @author WANG Minghao
 */
public enum StoreType {

    ITLACE("IT`LACE"),

    BOUTIQUE("24/7 Boutique"),

    LOUNGE("IT’LACE Lounge"),

    OUTLET("Outlet"),

    UNKNOWN("未知种类");

    private String desc;

    StoreType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static StoreType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToStoreType extends Castor<String, StoreType> {
        @Override
        public StoreType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return StoreType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToStoreType.class);
    }
}
