package nanshen.data.SystemUtil;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * @author WANG Minghao
 */
public enum PageType {

    HOME("首页", false),

    ITEM_LIST("列表页", true),

    LOGIN_PAGE("登陆页", false),

    ITEM_DETAIL("详情页", false),

    CART("购物车", false),

    WISH("心愿单", false),

    ORDER_LIST("订单列表", false),

    CUSTOMER_REVIEW_LIST("用户评价商品列表", false),

    CUSTOMER_REVIEW_CREATE("用户评价生成", false),

    ORDER("订单详情", false),

    CATEGORY("分类", true),

    TOPIC("专题", true),

    ABOUT("关于", true),

    BRAND("品牌", true),

    CONTACT("联系", true),

    WOYANG_HOME("首页", false),

    WOYANG_SEARCH("搜索页", false),

    UNKNOWN("未知状态", false);

    private String desc;

    private boolean needShow;

    PageType(String desc, boolean needShow) {
        this.desc = desc;
        this.needShow = needShow;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isNeedShow() {
        return needShow;
    }

    public static PageType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToArrivalStatus extends Castor<String, PageType> {
        @Override
        public PageType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return PageType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToArrivalStatus.class);
    }
}
