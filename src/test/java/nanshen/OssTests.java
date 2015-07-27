package nanshen;

import nanshen.service.api.oss.OssApi;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class OssTests {

    @Autowired
    private OssApi ossApi;

    @Test
    public void simple() throws Exception {
        try {
            Assert.assertTrue(ossApi.getObject("nanshen", "images/slider1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
