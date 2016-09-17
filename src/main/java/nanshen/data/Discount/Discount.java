package nanshen.data.Discount;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Discount
 *
 * @author WANG Minghao
 */
@Table("Discount")
public class Discount {

    /** ID */
    @Id
    private long id;

    /** userId, {@link nanshen.data.User.UserInfo#id} */
    @Column
    private long userId = 0;

    /** discount code */
    @Column
    private String code;

    /** discount code type */
    @Column
    private DiscountCodeType codeType = DiscountCodeType.MINUS;

    /** discount amount */
    @Column
    private long discount = 0;

    /** discount percentage */
    @Column
    private long percentage = 0;

    /** limitTotal price for discount */
    @Column
    private long limitTotal = 0;

    /** total price before discount */
    @Column
    private long total = 0;

    /** operator */
    @Column
    private long operator = 0;

    /** total times the code can be used */
    @Column
    private long times = 1;

    /** create time for this look, will fill when create */
    @Column
    private Date createTime = new Date();

    /** use time for this code */
    @Column
    private Date useTime = new Date();

    public Discount(String code, DiscountCodeType codeType, long discount, long limitTotal, long percentage, long times, long operator) {
        this.code = code;
        this.codeType = codeType;
        this.discount = discount;
        this.limitTotal = limitTotal;
        this.percentage = percentage;
        this.operator = operator;
        this.times = times;
    }

    public Discount() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DiscountCodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(DiscountCodeType codeType) {
        this.codeType = codeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLimitTotal() {
        return limitTotal;
    }

    public void setLimitTotal(long limitTotal) {
        this.limitTotal = limitTotal;
    }

    public long getPercentage() {
        return percentage;
    }

    public void setPercentage(long percentage) {
        this.percentage = percentage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public long getOperator() {
        return operator;
    }

    public void setOperator(long operator) {
        this.operator = operator;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }
}
