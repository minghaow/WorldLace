package nanshen.data;

/**
 * 对订单可以进行的操作，主要用于存储操作日志
 *
 * @author WANG Minghao
 */
public enum OrderOperation {

    ADD("添加新订单"),

    ALLOCATE_BUYER("分配订单"),

    CLEAR_VERIFY("清除审核状态"),

    CLEAR_CUSTOMER("清除客服状态"),

    VERIFY("申请审核"),

    VERIFY_PASS("审核通过"),

    VERIFY_IGNORE("审核忽略"),

    VERIFY_REFUSE("审核不通过"),

    VERIFY_CANCEL("审核不通过，取消订单"),

    LIMIT_SELL_CANCEL("商品限购，自动取消"),

    CHANGE_PRICE("修改价格"),

    CREATE_ORDER("生成订单"),

    CHANGE_ADDRESS("修改订单地址"),

    ORDER_PAYING("支付开始支付"),

    ORDER_PAYED("支付成功"),

    ALIPAY_FEEDBACK("支付宝回调"),

    COMPETING("抢单"),

    HOLD("认领"),

    RELEASE("取消认领"),

    AUTO_RELEASE("超时取消认领"),

    ADD_MERCHANT("添加采购订单信息"),

    DELETE_MERCHANT("删除采购订单信息"),

    ADD_SORTING("添加转运包裹信息"),

    DELETE_SORTING("删除转运包裹信息"),

    ADD_TRACKING("添加采购物流信息"),

    DELETE_TRACKING("删除采购物流信息"),

    ORDERED("确认采购信息"),

    LOCK("锁定"),

    UNLOCK("解锁"),

    CHANGE_COMPANY("修改转运公司"),

    FORCE_RECORD("强行报备"),

    FORCE_SORTING("强行分拣"),

    FORCE_CANCEL("强行取消"),

    FORCE_RESET("重置转运状态"),

    FORCE_FINISH("强制收货"),

    EFREIGHT_EMS("中外运EMS单号报备"),

    EFREIGHT_SORTING_EMS("中外运EMS单号分拣"),

    EFREIGHT_ERROR_STOCKOUT("中外运未分拣出库"),

    EFREIGHT_ERROR_TRACKING("中外运未分拣轨迹"),

    EFREIGHT_ERROR_CLEAR("中外运未分拣清关"),

    EFREIGHT_ERROR_DISPATCH("中外运未分拣派送"),

    EFREIGHT_SPECIAL_STOCKIN("中外运漏发入库，系统自动补入"),

    TRANSPORT_RECORD("报备"),

    TRANSPORT_SORTING("分拣"),

    TRANSPORT_DELETE_SORTING("转运公司取消分拣"),

    TRANSPORT_ARRIVE("入库"),

    TRANSPORT_STOCKOUT("出库"),

    TRANSPORT_CLEAR("清关"),

    TRANSPORT_DISPATCH("国内运输"),

    TRANSPORT_TRACKING("物流轨迹"),

    TRANSPORT_WEIGHT("称重"),

    TRANSPORT_DUTY("关税"),

    TRANSPORT_DUTY_C("关税确认"),

    KUAIDI_TRACKING("快递100物流轨迹"),

    QUEYR_TRACKING("物流订阅"),

    USER_FINISH("用户确认收货"),

    AUTO_FINISH("超时自动收货"),

    APPLY_SETTLE("买手申请结算"),

    CONFIRM_SETTLE("确认买手结算数据"),

    REFUSE_SETTLE("拒绝给买手结算"),

    SETTLED("结算给买手"),

    SETTLE_ERROR("结算给买手失败"),

    SETTLE_REFUSE("结算给买手被拒绝"),

    APPLY_T_SETTLE("转运公司申请结算"),

    CONFIRM_T_SETTLE("确认转运公司结算数据"),

    REFUSE_T_SETTLE("拒绝给转运公司结算"),

    T_SETTLED("结算给转运公司"),

    T_SETTLE_ERROR("结算给转运公司失败"),

    APPLY_RETURN("申请退款"),

    CONFIRM_RETURN("确认用户退款"),

    REFUSE_RETURN("拒绝给用户退款"),

    RETURN("给用户退款"),

    RETURN_ERROR("给用户退款失败"),

    RETURN_REFUSE("给用户退款被拒绝"),

    CHANGE_SETTLE_PRICE("修改买手结算金额"),

    CREATE_S_SETTLE("创建异常增项"),

    APPLY_S_SETTLE("申请异常增项"),

    CONFIRM_S_SETTLE("确认结算异常增项"),

    REFUSE_S_SETTLE("拒绝结算异常增项"),

    S_SETTLED("异常增项结算"),

    S_SETTLE_ERROR("异常增项结算出错"),

    S_SETTLE_REFUSE("异常增项结算被拒绝"),

    ADD_CUSTOMER_INFO("添加客服信息"),

    TAX_RETURN("退税"),

    TAX_RETURN_CFM("手动确认退税"),

    TAX_RETURN_ERROR("退税出错"),

    TRANS_FEE_RETURN("退转运费"),

    TRANS_FEE_RE_CFM("手动确认退转运费"),

    TRANS_FEE_RETURN_ER("退转运费出错"),

    SUPPLY_RETURN("余额转回"),

    SUPPLY_RETURN_ERROR("余额转回失败"),

    SMS("发送短信"),

    OVER_LIMIT_TRACKING("超时订单跟踪"),

    ADMIN_REMARK("管理员备注"),

    UNAVAILABLE("订单被取消，禁止操作"),

    TRANS_FEE_REPAY("补运费差价"),

    /** 当添加新操作时，旧的代码会将其转换为该值而不会抛出Exception */
    UNKNOWN("未知操作"),;

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
