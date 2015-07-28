package nanshen.service.api.oss;

import nanshen.data.ExecInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * ossApi
 *
 * @Author WANG Minghao
 */
public interface OssImageApi {

    String accessKeyId = "WE75bQnberCYgnN8";
    String accessKeySecret = "yls5Yk5aFOSirBJHT0Y5pbnW6J2ik4";
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";

    ExecInfo putObject(String bucketName, String key, String filePath) throws FileNotFoundException;

    ExecInfo putObject(String bucketName, String key, InputStream is, long length) throws FileNotFoundException;

    void listObjects(String bucketName);

    boolean getObject(String bucketName, String key) throws IOException;

    String getUrl(String key);

    String getLookImgUrl(long lookId, long imgId);

}
