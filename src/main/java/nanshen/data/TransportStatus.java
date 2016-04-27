package nanshen.data;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * @author WANG Minghao
 */
public enum TransportStatus {

    NEW("新添加"),

    STOCKIN("入库"),

    SORTING("分拣"),

    STOCKOUT("出库"),

    DISPATCH("派送"),

    REDISPATCH("再次派送"),

    FINISHED("到货"),

    RETURNING("退货中"),

    RETURNED("已退货"),

    UNKNOWN("未知状态");

    private String desc;

    TransportStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static TransportStatus get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToTransportStatus extends Castor<String, TransportStatus> {
        @Override
        public TransportStatus cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return TransportStatus.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToTransportStatus.class);
    }
}
