package nanshen.service.api.sms;

/**
 * 短信服务接口
 *
 * @Author WANG Minghao
 */
public interface SmsApi {

    boolean sendSms(String phone, String content);

    boolean sendVerifySms(String phone, String p1, String p2);

    boolean getAccountInfo();

}
