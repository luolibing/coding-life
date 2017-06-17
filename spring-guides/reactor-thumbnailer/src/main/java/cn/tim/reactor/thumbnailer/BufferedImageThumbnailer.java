package cn.tim.reactor.thumbnailer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.event.Event;
import reactor.function.Function;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by LuoLiBing on 15/11/4.
 * 函数式编程
 * 制作缩略图
 */
public class BufferedImageThumbnailer implements Function<Event<Path>, Path> {

    private final static ImageObserver DUMMY_OBSERVER = (img, infoflags, x, y, width, height) ->true;

    private final static Logger log = LoggerFactory.getLogger(BufferedImageThumbnailer.class);

    private final int maxLongSide;

    public BufferedImageThumbnailer(int maxLongSide) {
        this.maxLongSide = maxLongSide;
    }

    @Override
    public Path apply(Event<Path> pathEvent) {
        try {
            // 原图地址
            Path srcPath = pathEvent.getData();
            // 缩略图地址
            Path thumbnailPath = Files.createTempFile("thumbnail", "jpg").toAbsolutePath();
            BufferedImage imageIn = ImageIO.read(srcPath.toFile());
            // 缩略比
            double scale;
            // 宽图还是长图
            if(imageIn.getWidth() >= imageIn.getHeight()) {
                // 宽图
                scale = Math.max(imageIn.getWidth(), maxLongSide) / imageIn.getWidth();
            } else {
                // 长图
                scale = Math.max(imageIn.getHeight(), maxLongSide) /imageIn.getHeight();
            }

            // 输出图片
            BufferedImage thumbnailOut = new BufferedImage(
                    (int) scale * imageIn.getWidth(),
                    (int) scale * imageIn.getHeight(),
                    imageIn.getType());
            // 创建出图形
            Graphics2D graphics = thumbnailOut.createGraphics();
            // 转换格式
            AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
            // 在图形上画出对应的图
            graphics.drawImage(imageIn, transform, DUMMY_OBSERVER);
            ImageIO.write(thumbnailOut, "jpeg", thumbnailPath.toFile());
            log.info("图片" + srcPath + "已经缩略为" + thumbnailPath);
            return thumbnailPath;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
