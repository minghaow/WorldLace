package nanshen.data;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * SkuType
 *
 * @author WANG Minghao
 */
public enum SkuType {

    UPWEAR("上衣", new SkuDetailType[]{SkuDetailType.OUTWEAR, SkuDetailType.SHIRTS, SkuDetailType.SUITS,
            SkuDetailType.SWEATERS, SkuDetailType.TEE}),

    DOWNWEAR("裤子", new SkuDetailType[]{SkuDetailType.PANTS, SkuDetailType.JEANS, SkuDetailType.SHORTS}),

    SHOES("鞋靴", new SkuDetailType[]{SkuDetailType.SHOES}),

    ACCESSORIES("配饰", new SkuDetailType[]{SkuDetailType.ACCESSORIES}),

    UNKNOWN("未知种类", new SkuDetailType[]{});

    private String desc;

    private SkuDetailType[] relatedDetailType;

    SkuType(String desc, SkuDetailType[] SkuDetailTypeList) {
        this.desc = desc;
        this.relatedDetailType = SkuDetailTypeList;
    }

    public String getDesc() {
        return desc;
    }

    public SkuDetailType[] getRelatedDetailType() {
        return relatedDetailType;
    }

    public void setRelatedDetailType(SkuDetailType[] relatedDetailType) {
        this.relatedDetailType = relatedDetailType;
    }

    public static SkuType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToSkuType extends Castor<String, SkuType> {
        @Override
        public SkuType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return SkuType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToSkuType.class);
    }
}
