package nanshen.data.Discount;

import nanshen.utils.LogUtils;
import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * DiscountCodeType
 *
 * @author WANG Minghao
 */
public enum DiscountCodeType implements DiscountCalculator {

    MINUS("直减") {
        @Override
        public long calculateAmount(long total, long discount, long percent, long limit) {
            return discount;
        }
    },

    PERCENT("折扣") {
        @Override
        public long calculateAmount(long total, long discount, long percent, long limit) {
            LogUtils.info("total: " + total);
            LogUtils.info("discount: " + discount);
            LogUtils.info("percent: " + percent);
            LogUtils.info("limit: " + limit);
            return (100 - percent) * total / 100;
        }
    },

    MINUS_OVER("满减") {
        @Override
        public long calculateAmount(long total, long discount, long percent, long limit) {
            if (total < limit) {
                return 0;
            }
            return discount;
        }
    },

    PERCENT_OVER("满折") {
        @Override
        public long calculateAmount(long total, long discount, long percent, long limit) {
            if (total < limit) {
                return 0;
            }
            return (100 - percent) * total / 100;
        }
    },

    UNKNOWN("未知种类") {
        @Override
        public long calculateAmount(long total, long discount, long percent, long limit) {
            return 0;
        }
    };

    private String desc;

    DiscountCodeType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public long calculateAmount(long total, long discount, long percent, long limit) {
        return 0;
    }

    public static DiscountCodeType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuType extends Castor<String, DiscountCodeType> {
        @Override
        public DiscountCodeType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return DiscountCodeType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuType.class);
    }
}
