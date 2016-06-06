package nanshen.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图片处理相关的工具类
 *
 * @author WANG Minghao
 */
public class ImageUtils {

    public static BufferedImage resize(BufferedImage image, int maxWidth, int maxHeight) {
        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();
        double scaleRate = Math.min(1.0 * maxWidth / srcWidth, 1.0 * maxHeight / srcHeight);
        scaleRate = Math.min(1.0, scaleRate);
        Integer targetWidth = (int) (srcWidth * scaleRate);
        Integer targetHeight = (int) (srcHeight * scaleRate);
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return scaledImage;
    }

    public static byte[] getData(BufferedImage image) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
            return os.toByteArray();
        } finally {
            os.close();
        }
    }
}
