package nanshen.service.api.sms;

import nanshen.dao.SmsDao;
import nanshen.data.Sms;
import nanshen.utils.HttpUtils;
import nanshen.utils.LogUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务接口
 *
 * @Author WANG Minghao
 */
@Service
public class SmsApiImpl implements SmsApi {

    private static final String UID = "DSSa0pUXQXE4";
    private static final String CID = "d9Z7uIKCYqhQ";
    private static final String PASSWORD = "bpafm6v5";
    private static final String TEMPLATE_CONTENT = "【商讯达】您的验证码：5768，三分钟内有效";
    private static final String TEMPLATE_CONTENT_VERIFY = "【桃源网】尊敬的用户，你的手机验证码是：%P%，3分钟内有效。请不要把此验证码泄露给任何人，以便你能安全使用。";

    @Autowired
    private SmsDao smsDao;

    @Override
    public boolean sendSms(String phone, String content) {
        boolean isSuccess = sms_api1(phone, TEMPLATE_CONTENT);
        smsDao.insert(new Sms(content, isSuccess, phone));
        return isSuccess;
    }

    @Override
    public boolean sendVerifySms(String phone, String p1, String p2) {
        boolean isSuccess = sms_api2(phone, p1, p2);
        smsDao.insert(new Sms("验证短信模板号[" + CID + "] " + p1 + " " + p2, isSuccess, phone));
        return isSuccess;
    }

    @Override
    public boolean getAccountInfo() {
        return account_api();
    }

    private static Map<String, String> prepareBasicParam(String phone) {

        Map<String, String> para = new HashMap<String, String>();

        /**
         * 微米账号的接口UID
         */
        para.put("uid", UID);

        /**
         * 微米账号的接口密码
         */
        para.put("pas", PASSWORD);

        /**
         * 接口返回类型：json、xml、txt。默认值为txt
         */
        para.put("type", "json");

        if (!StringUtils.isBlank(phone)) {
            /**
             * 目标手机号码，多个以“,”分隔，一次性调用最多100个号码，示例：139********,138********
             */
            para.put("mob", phone);
        }

        return para;

    }

    /**
     * 短信接口一，自写短信内容。该接口提交的短信均由人工审核，下发后请联系在线客服。适合：节假日祝福、会员营销群发等。
     */
    private static boolean sms_api1(String phone, String content) {

        Map<String, String> para = prepareBasicParam(phone);

        /**
         * 短信内容。必须设置好短信签名，签名规范：
         * 1、短信内容一定要带签名，签名放在短信内容的最前面；
         * 2、签名格式：【***】，签名内容为三个汉字以上（包括三个）；
         * 3、短信内容不允许双签名，即短信内容里只有一个“【】”
         *
         */
        para.put("con", content);

        try {
//            注释掉GET方法版本，如有需要，未来可以切换
//            LogUtils.info(HttpUtils.convertStreamToString(
//                    HttpUtils.get("http://api.weimi.cc/2/sms/send.html", para), "UTF-8"));
            LogUtils.info(HttpUtils.convertStreamToString(
                    HttpUtils.post("http://api.weimi.cc/2/sms/send.html", para), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * 短信接口二，触发类模板短信接口，可以设置动态参数变量。适合：验证码、订单短信等。
     */
    private static boolean sms_api2(String phone, String p1, String p2) {

        Map<String, String> para = prepareBasicParam(phone);

        /**
         * 短信模板cid，通过微米后台创建，由在线客服审核。必须设置好短信签名，签名规范：
         * 1、模板内容一定要带签名，签名放在模板内容的最前面；
         * 2、签名格式：【***】，签名内容为三个汉字以上（包括三个）；
         * 3、短信内容不允许双签名，即短信内容里只有一个“【】”
         */
        para.put("cid", CID);

        /**
         * 传入模板参数。
         *
         * 短信模板示例：
         * 【微米】您的验证码是：%P%，%P%分钟内有效。如非您本人操作，可忽略本消息。
         *
         * 传入两个参数：
         * p1：610912
         * p2：3
         * 最终发送内容：
         * 【微米】您的验证码是：610912，3分钟内有效。如非您本人操作，可忽略本消息。
         */
        para.put("p1", p1);
        para.put("p2", p2);

        try {
//            注释掉GET方法版本，如有需要，未来可以切换
//            LogUtils.info(HttpUtils.convertStreamToString(
//                    HttpUtils.get("http://api.weimi.cc/2/sms/send.html", para), "UTF-8"));
            LogUtils.info(HttpUtils.convertStreamToString(
                    HttpUtils.post("http://api.weimi.cc/2/sms/send.html", para), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * 微米账号查询
     */
    private static boolean account_api() {

        Map<String, String> para = prepareBasicParam(null);

        try {
            LogUtils.info(HttpUtils.convertStreamToString(
                    HttpUtils.get("http://api.weimi.cc/2/account/balance.html", para), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
