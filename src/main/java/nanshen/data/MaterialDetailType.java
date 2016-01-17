package nanshen.data;

import org.nutz.castor.Castor;
import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;

/**
 * MaterialDetailType
 *
 * @author WANG Minghao
 */
public enum MaterialDetailType {

    IRON_GLASS("钢化玻璃"),
    CRYSTAL("水晶"),
    CHINA_1("陶器"),
    CHINA_2("紫砂"),
    CHINA_3("硬质瓷"),
    CHINA_4("骨质瓷"),
    CHINA_5("强化瓷"),
    IRON_1("不锈钢"),
    IRON_2("铝"),
    IRON_3("铸铁"),
    IRON_4("银器"),
    IRON_5("搪瓷"),
    IRON_6("铜"),
    CLOTH_1("棉"),
    CLOTH_2("亚麻"),
    TREE_1("竹子"),
    TREE_2("鸡翅木"),
    CHEMICAL_1("密胺"),
    CHEMICAL_2("PVC"),
    BUNDLE("组合材料"),
    STONE("石头"),
    UNKNOWN("未知类型");

    private String desc;

    MaterialDetailType(String desc) {
        this.desc = desc;
    }

    MaterialDetailType() {
    }

    public String getDesc() {
        return desc;
    }

    public static MaterialDetailType get(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 当数据库中出现未知的状态时，该转换器会自动将其转换为{@link #UNKNOWN}，而不会报错
     */
    public static class StringToMaterialDetailType extends Castor<String, MaterialDetailType> {
        @Override
        public MaterialDetailType cast(String src, Class<?> toType, String... args) throws FailToCastObjectException {
            return MaterialDetailType.get(src);
        }

    }

    /**
     * 将转换器添加到Nutz
     */
    static {
        Castors.me().addCastor(StringToMaterialDetailType.class);
    }
}
