package nanshen.service.api.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import nanshen.constant.SystemConstants;
import nanshen.data.ExecInfo;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * OssFormalApiImpl
 *
 * @Author WANG Minghao
 */
@Service
public class OssFormalApiImpl implements OssFormalApi {

    private static ClientConfiguration conf;

    static {
        conf = new ClientConfiguration();

        // 设置HTTP最大连接数为10
        conf.setMaxConnections(10);

        // 设置TCP连接超时为5000毫秒
        conf.setConnectionTimeout(5000);

        // 设置最大的重试次数为3
        conf.setMaxErrorRetry(3);

        // 设置Socket传输数据超时的时间为2000毫秒
        conf.setSocketTimeout(2000);
    }

    @Override
    public ExecInfo putObject(String bucketName, String key, String filePath) throws FileNotFoundException {

        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 获取指定文件的输入流
        File file = new File(filePath);
        InputStream content = new FileInputStream(file);

        // 上传Object
        return uploadObject(bucketName, key, client, content, file.length());
    }

    @Override
    public ExecInfo putObject(String bucketName, String key, InputStream is, long length) throws FileNotFoundException {

        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传Object
        return uploadObject(bucketName, key, client, is, length);
    }

    private ExecInfo uploadObject(String bucketName, String key, OSSClient client, InputStream content, long length) {
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();

        // 必须设置ContentLength
        meta.setContentLength(length);

        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, content, meta);

        if (result == null || result.getETag() == null) {
            String resultString = result == null ? "" : result.getETag();
            return ExecInfo.fail("上传失败 " + resultString);
        }

        // 打印ETag
        System.out.println(result.getETag());
        return ExecInfo.succ();
    }

    @Override
    public void listObjects(String bucketName) {

        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = client.listObjects(bucketName);

        // 遍历所有Object
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }

    @Override
    public boolean getObject(String bucketName, String key) throws IOException {

        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = client.getObject(bucketName, key);
        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();
        // 处理Object
        if (objectContent == null) {
            return false;
        }
        File f = new File("/Users/minghaowang/Desktop/slider.png");
        inputstreamtofile(objectContent, f);
        // 关闭流
        objectContent.close();
        return true;
    }

    @Override
    public String getUrl(String key) {
        try {
            return new URL(SystemConstants.OSS_BASE_HTTP + "/" + SystemConstants.BUCKET_NAME + "/" + key).toString();
        } catch (MalformedURLException e) {
            return "";
        }
    }

    @Override
    public String getLookImgUrl(long lookId, long imgId) {
        try {
            return new URL(SystemConstants.OSS_BASE_HTTP + "/images/look/" + lookId + "/" + imgId).toString();
        } catch (MalformedURLException e) {
            return "";
        }
    }

    @Override
    public String getSkuImgUrl(long skuId, long imgId) {
        try {
            return new URL(SystemConstants.OSS_BASE_HTTP + "/images/sku/" + skuId + "/" + imgId).toString();
        } catch (MalformedURLException e) {
            return "";
        }
    }

    private static void inputstreamtofile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
