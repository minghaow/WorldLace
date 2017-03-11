package nanshen.service.api.weixinpay.protocol;

/**
 * NanShen
 *
 * @Author WANG Minghao
 */
public class Payform {

    private String code;              //通过code换取微信的openid,谁支付的
    private Integer transactionID;    //交易ID
    private String appId;             //公众账号ID
    private String timeStamp;         //时间戳
    private String nonceStr;          //随机字符串
    private String packages;          //订单详情扩展字符串
    private String paySign;           //签名
    private String signType = "MD5";            //微信openid
    private String openid;            //微信openid
    private Integer masterOrderID;    //订单ID
    //set get

    public Payform() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMasterOrderID() {
        return masterOrderID;
    }

    public void setMasterOrderID(Integer masterOrderID) {
        this.masterOrderID = masterOrderID;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
