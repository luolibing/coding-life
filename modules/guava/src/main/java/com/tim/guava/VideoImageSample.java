package com.tim.guava;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * http://www.jcodec.org/
 * jcodec处理视频
 * Created by luolibing on 2018/2/7.
 */
public class VideoImageSample {

    public static void getFirstFrame() throws IOException, JCodecException {
        int frameNumber = 42;
        Picture picture = FrameGrab.getFrameFromFile(new File("video.mp4"), frameNumber);

        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bufferedImage, "png", new File("frame42.png"));
    }

    /**
     * 获取视频的指定帧
     * @throws IOException
     * @throws JCodecException
     */
    public static void getAllFrame() throws IOException, JCodecException {
        double startSec = 51.632;
        int frameCount = 10;
        File file = new File("video.mp4");

        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
        // 跳转到指定的秒数
        grab.seekToSecondPrecise(startSec);

        // 取10帧
        for (int i=0;i<frameCount;i++) {
            Picture picture = grab.getNativeFrame();
            System.out.println(picture.getWidth() + "x" + picture.getHeight() + " " + picture.getColor());
            //for JDK (jcodec-javase)
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bufferedImage, "png", new File("frame"+i+".png"));
        }
    }
}
