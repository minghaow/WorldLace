package nanshen.data;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * MaterialType
 *
 * @author WANG Minghao
 */
public enum MaterialType {

    GLASS("玻璃制品", new MaterialDetailType[]{
            MaterialDetailType.IRON_GLASS,
            MaterialDetailType.CRYSTAL
    }),

    IRON("金属", new MaterialDetailType[]{
            MaterialDetailType.IRON_1,
            MaterialDetailType.IRON_2,
            MaterialDetailType.IRON_3,
            MaterialDetailType.IRON_4,
            MaterialDetailType.IRON_5,
            MaterialDetailType.IRON_6
    }),

    CHINA("陶瓷", new MaterialDetailType[]{
            MaterialDetailType.CHINA_1,
            MaterialDetailType.CHINA_2,
            MaterialDetailType.CHINA_3,
            MaterialDetailType.CHINA_4,
            MaterialDetailType.CHINA_5
    }),

    CLOTH("纺织品", new MaterialDetailType[]{
            MaterialDetailType.CLOTH_1,
            MaterialDetailType.CLOTH_2
    }),

    TREE("竹木", new MaterialDetailType[]{
            MaterialDetailType.TREE_1,
            MaterialDetailType.TREE_2
    }),

    CHEMICAL("高分子", new MaterialDetailType[]{
            MaterialDetailType.CHEMICAL_1,
            MaterialDetailType.CHEMICAL_2
    }),

    BUNDLE("组合材料", new MaterialDetailType[]{
            MaterialDetailType.BUNDLE
    }),

    STONE("石头", new MaterialDetailType[]{
        MaterialDetailType.STONE
    }),

    UNKNOWN("未知种类", new MaterialDetailType[]{});

    private String desc;

    private MaterialDetailType[] materialDetailTypeList;

    MaterialType(String desc, MaterialDetailType[] materialDetailTypeList) {
        this.desc = desc;
        this.materialDetailTypeList = materialDetailTypeList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MaterialDetailType[] getMaterialDetailTypeList() {
        return materialDetailTypeList;
    }

    public void setMaterialDetailTypeList(MaterialDetailType[] materialDetailTypeList) {
        this.materialDetailTypeList = materialDetailTypeList;
    }

    public static MaterialType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToMaterialType extends Castor<String, MaterialType> {
        @Override
        public MaterialType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return MaterialType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToMaterialType.class);
    }
}
