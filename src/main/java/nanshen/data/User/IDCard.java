package nanshen.data.User;

import nanshen.constant.DateConstants;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * 用户身份证信息数据表
 *
 * @author WANG Minghao
 */
@Table("IDCard")
public class IDCard {

    /** 数据库主键 */
    @Id
    private long id;

    /** 用户ID */
    @Column
    private long userId;

    /** 身份证姓名 */
    @Column
    private String cardName;

    /** 身份证号 */
    @Column
    private String cardNo;

    /** 身份证正面图片ID */
    @Column
    private long frontImageId;

    /** 身份证背面图片ID */
    @Column
    private long backImageId;

    @Column
    private Date createTime = new Date();

    @Column
    private Date updateTime = new Date();

    @Column
    private boolean display;

    @Column
    private Date expireDate = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public long getFrontImageId() {
        return frontImageId;
    }

    public void setFrontImageId(long frontImageId) {
        this.frontImageId = frontImageId;
    }

    public long getBackImageId() {
        return backImageId;
    }

    public void setBackImageId(long backImageId) {
        this.backImageId = backImageId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public String getExpireDateForDisplay() {
        return DateConstants.YYYYMM.format(expireDate);
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}
