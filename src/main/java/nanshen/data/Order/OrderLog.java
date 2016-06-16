package nanshen.data.Order;

import nanshen.utils.ViewUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * 记录订单操作日志的数据表
 *
 * @author WANG Minghao
 */
@Table("OrderLog")
public class OrderLog {

    /** 数据库主键 */
    @Id
    private long id;

    @Column
    private long orderId;

    @Column
    private long userId;

    private String username = "";

    @Column
    private OrderOperation operation = OrderOperation.UNKNOWN;

    @Column
    private Date operateTime = new Date();

    @Column
    private String extraInfo = "";

    public OrderLog(long orderId, long userId, OrderOperation operation, String extraInfo) {
        this.orderId = orderId;
        this.userId = userId;
        this.operation = operation;
        this.operateTime = new Date();
        this.extraInfo = extraInfo;
    }

    public OrderLog() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OrderOperation getOperation() {
        return operation;
    }

    /**
     * nutz使用的用于将数据库字段值转换为{@link OrderOperation}的方法
     *
     * @param operationName
     */
    public void setOperation(String operationName) {
        this.operation = OrderOperation.get(operationName);
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public String getConvertedOperateTime() {
        return ViewUtils.convertDateToString(operateTime);
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setOperation(OrderOperation operation) {
        this.operation = operation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
