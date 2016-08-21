package nanshen.data.Sku;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * SalesInfo, for sku
 *
 * @author WANG Minghao
 */
@Table("SalesInfo")
public class SalesInfo {

    /** ID */
    @Id
    private long id;

    /** ID, {@link SkuItem#id} */
    @Column
    private long itemId;

    /** ID, {@link SkuDetail#id} */
    @Column
    private long skuId;

    /** total sales amount*/
    @Column
    private long totalAmount = 0;

    /** monthly sales amount */
    @Column
    private long monthlyAmount = 0;

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** update time for this look, all operator will update this value */
    @Column
    private Date updateTime = new Date();

    public SalesInfo() {
    }

    public SalesInfo(Date createTime, long id, long itemId, long monthlyAmount, long skuId, long totalAmount, Date updateTime) {
        this.createTime = createTime;
        this.id = id;
        this.itemId = itemId;
        this.monthlyAmount = monthlyAmount;
        this.skuId = skuId;
        this.totalAmount = totalAmount;
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(long monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
