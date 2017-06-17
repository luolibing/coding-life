package cn.design.patterns.proxy.virtual;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LuoLiBing on 16/6/23.
 */
public class ImageProxyTestDrive {

    Component imageComponent;

    public static void main(String[] args) throws MalformedURLException {
        new ImageProxyTestDrive();
    }

    public ImageProxyTestDrive() throws MalformedURLException {

        JFrame jFrame = new JFrame();
        Icon icon = new ImageProxy(new URL("http://cnews.chinadaily.com.cn/img/attachement/jpg/site1/20160623/d8cb8a14fb9018d5d7fd30.jpg"));
        imageComponent = new ImageComponent(icon);
        jFrame.getContentPane().add(imageComponent);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800,600);
        jFrame.setVisible(true);
    }

}
