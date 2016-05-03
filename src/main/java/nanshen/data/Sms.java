package nanshen.data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * SkuItem
 *
 * @author WANG Minghao
 */
@Table("Sms")
public class Sms {

    /** ID */
    @Id
    private long id;

    /** sms content */
    @Column
    private String content;

    /** sms target phone number */
    @Column
    private String phone;

    /** is sent successfully */
    @Column
    private boolean isSuccess = true;

    /** send time */
    @Column
    private Date sendTime = new Date();

    public Sms(String content, long id, boolean isSuccess, String phone, Date sendTime) {
        this.content = content;
        this.id = id;
        this.isSuccess = isSuccess;
        this.phone = phone;
        this.sendTime = sendTime;
    }

    public Sms(String content, boolean isSuccess, String phone) {
        this.content = content;
        this.isSuccess = isSuccess;
        this.phone = phone;
        this.sendTime = new Date();
    }

    public Sms() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
