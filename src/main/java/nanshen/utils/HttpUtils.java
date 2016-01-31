package nanshen.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 提供http请求，并返回response的功能
 *
 * @author WANG Minghao
 */
@Utils
public class HttpUtils {

    public static String get(String url) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            LogUtils.info(responseBody);
            httpclient.getConnectionManager().shutdown();
            return responseBody;
        } catch (Exception e) {
            LogUtils.info("[HttpUtils] Exception" + " " + url + " " + e.getClass());
        }
        return "";
    }
}

