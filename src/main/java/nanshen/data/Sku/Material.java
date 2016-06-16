package nanshen.data.Sku;

/**
 * MaterialType
 *
 * @author WANG Minghao
 */
public class Material {

    /**
     * Material type
     */
    private MaterialType materialType = MaterialType.UNKNOWN;

    /**
     * Material detail type
     */
    private MaterialDetailType materialDetailType = MaterialDetailType.UNKNOWN;

    public Material(MaterialDetailType materialDetailType, MaterialType materialType) {
        this.materialDetailType = materialDetailType;
        this.materialType = materialType;
    }

    public Material() {
    }

    public MaterialDetailType getMaterialDetailType() {
        return materialDetailType;
    }

    public void setMaterialDetailType(MaterialDetailType materialDetailType) {
        this.materialDetailType = materialDetailType;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

}
