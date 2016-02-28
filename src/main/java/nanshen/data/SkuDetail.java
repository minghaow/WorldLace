package nanshen.data;

import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * SkuInfo
 *
 * @author WANG Minghao
 */
@Table("SkuDetail")
public class SkuDetail {

    /** ID, {@link SkuInfo#id} */
    @Id
    private long skuId;

    /** origin price, price unit: RMB */
    @Column
    private long originPrice;

    /** price, price unit: RMB */
    @Column
    private long price;

    /** cost, cost unit: RMB */
    @Column
    private long cost;

    /** Material type */
    @Column
    private MaterialType materialType;

    /** Material detail type */
    @Column
    private MaterialDetailType materialDetailType;

    /** weight, weight unit: g */
    @Column
    private long weight;

    /** volume */
    private Volume volume;

    /** volumeInDb */
    @Column
    private String volumeInDb;

    /** origin product area */
    @Column
    private String productArea;

    /** brand */
    @Column
    private String brand;

    /** product company */
    @Column
    private String company;

    /** option 1 type */
    @Column
    private OptionType option1Type;

    /** option 2 type */
    @Column
    private OptionType option2Type;

    /** option 1 */
    @Column
    private String option1;

    /** option 2 */
    @Column
    private String option2;

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public SkuDetail(String brand, String company, long cost, Date createTime, MaterialDetailType materialDetailType,
                     MaterialType materialType, long price, String productArea, long skuId, Date updateTime,
                     Volume volume, String volumeInDb, long weight, long originPrice) {
        this.brand = brand;
        this.company = company;
        this.cost = cost;
        this.createTime = createTime;
        this.materialDetailType = materialDetailType;
        this.materialType = materialType;
        this.price = price;
        this.productArea = productArea;
        this.skuId = skuId;
        this.updateTime = updateTime;
        this.volume = volume;
        this.volumeInDb = volumeInDb;
        this.weight = weight;
        this.originPrice = originPrice;
    }

    public SkuDetail() {
    }

    public long getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(long originPrice) {
        this.originPrice = originPrice;
    }

    public String getDisplayOriginPrice() {
        return ViewUtils.priceConverter(originPrice);
    }

    public String getDisplayPrice() {
        return ViewUtils.priceConverter(price);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public String getVolumeInDb() {
        return volumeInDb;
    }

    public void setVolumeInDb(String volumeInDb) {
        this.volumeInDb = volumeInDb;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public OptionType getOption1Type() {
        return option1Type;
    }

    public void setOption1Type(OptionType option1Type) {
        this.option1Type = option1Type;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public OptionType getOption2Type() {
        return option2Type;
    }

    public void setOption2Type(OptionType option2Type) {
        this.option2Type = option2Type;
    }
}
