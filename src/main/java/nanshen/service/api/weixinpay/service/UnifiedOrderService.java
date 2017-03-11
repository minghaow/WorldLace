package nanshen.service.api.weixinpay.service;

import nanshen.service.api.weixinpay.common.Configure;
import nanshen.service.api.weixinpay.common.RandomStringGenerator;
import nanshen.service.api.weixinpay.protocol.unified_order.UnifiedOrderReqData;

/**
 * User: Wang Minghao
 * Date: 2016/10/29
 * Time: 16:03
 */
public class UnifiedOrderService extends BaseService{

    public UnifiedOrderService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.UNIFIED_ORDER_API);
    }

    public UnifiedOrderReqData prepareRequestData(String body, String openid, long showOrderId, String ip, long total) {
        return new UnifiedOrderReqData(
                Configure.getAppid(),
                body,
                Configure.getMchid(),
                RandomStringGenerator.getRandomStringByLength(32),
                Configure.NOTIFY_URL,
                openid,
                "" + showOrderId,
                ip,
                (int)total,
                "JSAPI"
        );
    }

    /**
     * 请求支付服务
     * @param unifiedOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedOrderReqData unifiedOrderReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(unifiedOrderReqData);

        return responseString;
    }
}
