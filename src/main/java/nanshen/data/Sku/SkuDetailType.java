package nanshen.data.Sku;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SkuType
 *
 * @author WANG Minghao
 */
public enum SkuDetailType {

    OUTWEAR("外套"),

    SUITS("正装"),

    SWEATERS("毛衣"),

    SHIRTS("衬衣"),

    TEE("T恤"),

    JEANS("牛仔裤"),

    SHORTS("短裤"),

    PANTS("裤装"),

    SHOES("鞋靴"),

    ACCESSORIES("配饰"),

    UNKNOWN("未知种类");

    private String desc;

    SkuDetailType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static SkuDetailType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuDetailType extends Castor<String, SkuDetailType> {
        @Override
        public SkuDetailType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuDetailType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuDetailType.class);
    }
}
