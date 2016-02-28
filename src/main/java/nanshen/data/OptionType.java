package nanshen.data;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * @author WANG Minghao
 */
public enum OptionType {

    COMBINATION("组合", false),

    COUNT("数量", true),

    COLOR("颜色", false),

    SERVICE("服务种类", false),

    UNKNOWN("未知", false);

    private String desc;

    private boolean priceDecider;

    OptionType(String desc, boolean priceDecider) {
        this.desc = desc;
        this.priceDecider = priceDecider;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isPriceDecider() {
        return priceDecider;
    }

    public static OptionType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToArrivalStatus extends Castor<String, OptionType> {
        @Override
        public OptionType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return OptionType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToArrivalStatus.class);
    }
}
