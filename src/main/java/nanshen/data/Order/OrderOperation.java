package nanshen.data.Order;

/**
 * 对订单可以进行的操作，主要用于存储操作日志
 *
 * @author WANG Minghao
 */
public enum OrderOperation {

    ADD("添加新订单"),

    CREATE_ORDER("生成订单"),

    CHANGE_ADDRESS("修改订单地址"),

    ORDER_PAYING("支付开始支付"),

    ORDER_PAYED("支付成功"),

    ALIPAY_FEEDBACK("支付宝回调"),

    USER_FINISH("用户确认收货"),

    AUTO_FINISH("超时自动收货"),

    SMS("发送短信"),

    OVER_LIMIT_TRACKING("超时订单跟踪"),

    ADMIN_REMARK("管理员备注"),

    UNAVAILABLE("订单被取消，禁止操作"),

    ADMIN_CONFIRM("管理员确认订单"),

    SHIPPING("开始运输"),

    CANCEL_ORDER("取消订单"),

    /** 当添加新操作时，旧的代码会将其转换为该值而不会抛出Exception */
    UNKNOWN("未知操作");

    private String desc;

    OrderOperation(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderOperation get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

}
