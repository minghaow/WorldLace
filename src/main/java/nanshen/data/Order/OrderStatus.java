package nanshen.data.Order;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * @author WANG Minghao
 */
public enum OrderStatus {

    NEW("待支付"),

    PAYING("正在支付"),

    PAYED("已支付"),

    CONFIRMED("订单已确认"),

    REFUSED("订单已拒绝"),

    SHIPPING("运输中"),

    FINISHED("到货"),

    REVIEWED("已评价"),

    RETURNING("退货中"),

    RETURNED("已退货"),

    UNKNOWN("未知状态");

    private String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderStatus get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToOrderStatus extends Castor<String, OrderStatus> {
        @Override
        public OrderStatus cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return OrderStatus.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToOrderStatus.class);
    }
}
