package nanshen.service.api.weixinpay.business;

import nanshen.service.api.weixinpay.common.Configure;
import nanshen.service.api.weixinpay.common.Util;
import nanshen.service.api.weixinpay.common.report.ReporterFactory;
import nanshen.service.api.weixinpay.common.report.protocol.ReportReqData;
import nanshen.service.api.weixinpay.common.report.service.ReportService;
import nanshen.service.api.weixinpay.protocol.downloadbill_protocol.DownloadBillReqData;
import nanshen.service.api.weixinpay.protocol.downloadbill_protocol.DownloadBillResData;
import nanshen.service.api.weixinpay.service.DownloadBillService;
import nanshen.utils.LogUtils;

/**
 * User: rizenguo
 * Date: 2014/12/3
 * Time: 10:45
 */
public class DownloadBillBusiness {

    public DownloadBillBusiness() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        downloadBillService = new DownloadBillService();
    }

    public interface ResultListener{
        //API返回ReturnCode不合法，支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(DownloadBillResData downloadBillResData);

        //API返回ReturnCode为FAIL，支付API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(DownloadBillResData downloadBillResData);

        //下载对账单失败
        void onDownloadBillFail(String response);

        //下载对账单成功
        void onDownloadBillSuccess(String response);

    }

    //执行结果
    private static String result = "";

    private DownloadBillService downloadBillService;

    /**
     * 请求对账单下载服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */

    public void run(DownloadBillReqData downloadBillReqData,ResultListener resultListener) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“对账单API”所需要提交的数据
        //--------------------------------------------------------------------

        //API返回的数据
        String downloadBillServiceResponseString;

        long costTimeStart = System.currentTimeMillis();

        //支持加载本地测试数据进行调试

        LogUtils.info("对账单API返回的数据如下：");
        downloadBillServiceResponseString = downloadBillService.request(downloadBillReqData);


        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        LogUtils.info("api请求总耗时：" + totalTimeCost + "ms");

        LogUtils.info(downloadBillServiceResponseString);

        DownloadBillResData downloadBillResData;

        String returnCode = "";
        String returnMsg = "";

        try {
            //注意，这里失败的时候是返回xml数据，成功的时候反而返回非xml数据
            downloadBillResData = (DownloadBillResData) Util.getObjectFromXML(downloadBillServiceResponseString, DownloadBillResData.class);

            if (downloadBillResData == null || downloadBillResData.getReturn_code() == null) {
                setResult("Case1:对账单API请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
                resultListener.onFailByReturnCodeError(downloadBillResData);
                return;
            }
            if (downloadBillResData.getReturn_code().equals("FAIL")) {
                ///注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
                setResult("Case2:对账单API系统返回失败，请检测Post给API的数据是否规范合法");
                resultListener.onFailByReturnCodeFail(downloadBillResData);
                returnCode = "FAIL";
                returnMsg = downloadBillResData.getReturn_msg();
            }
        } catch (Exception e) {
            //注意，这里成功的时候是直接返回纯文本的对账单文本数据，非XML格式
            if (downloadBillServiceResponseString.equals(null) || downloadBillServiceResponseString.equals("")) {
                setResult("Case4:对账单API系统返回数据为空");
                resultListener.onDownloadBillFail(downloadBillServiceResponseString);
            } else {
                setResult("Case3:对账单API系统成功返回数据");
                resultListener.onDownloadBillSuccess(downloadBillServiceResponseString);
            }
            returnCode = "SUCCESS";
        } finally {

            ReportReqData reportReqData = new ReportReqData(
                    downloadBillReqData.getDevice_info(),
                    Configure.DOWNLOAD_BILL_API,
                    (int) (totalTimeCost),//本次请求耗时
                    returnCode,
                    returnMsg,
                    "",
                    "",
                    "",
                    "",
                    Configure.getIP()
            );

            long timeAfterReport;
            if(Configure.isUseThreadToDoReport()){
                ReporterFactory.getReporter(reportReqData).run();
                timeAfterReport = System.currentTimeMillis();
                Util.log("pay+report总耗时（异步方式上报）："+(timeAfterReport-costTimeStart) + "ms");
            }else{
                ReportService.request(reportReqData);
                timeAfterReport = System.currentTimeMillis();
                Util.log("pay+report总耗时（同步方式上报）："+(timeAfterReport-costTimeStart) + "ms");
            }
        }
    }

    public void setDownloadBillService(DownloadBillService service) {
        downloadBillService = service;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        DownloadBillBusiness.result = result;
    }

    public void setResult(String result,String type){
        setResult(result);
    }

}