package cn.design.patterns.proxy.virtual;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by LuoLiBing on 16/6/23.
 * IconImage对象加载比较耗费资源与时间
 * ImageProxy作为ImageIcon的代理,两者都实现了同一接口,所以可以一视同仁,ImageProxy每个方法都提供了两个分支,一个没有加载完毕的默认设置,一个加载完毕的设置
 *
 */
public class ImageProxy implements Icon {

    private ImageIcon imageIcon;

    private URL imageURL;

    Thread retrievalThread;

    boolean retrieving = false;

    public ImageProxy(URL url) {
        this.imageURL = url;
    }

    /**
     * 这个代理会被调用2次
     * 第一次进行IconImage没有加载,会走下面的加载流程
     * 第二次进行IconImage已经加载完毕,会直接进行渲染
     * @param c
     * @param g
     * @param x
     * @param y
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // 如果ImageIcon已经加载完毕,则直接paintIcon
        if(imageIcon != null) {
            imageIcon.paintIcon(c, g, x, y);
        } else {
            // 否则先显示一个默认的图片或者消息
            g.drawString("Loading CD cover, please wait...", x+300, y+200);
            // 是否尝试加载,没有尝试加载才尝试去加载这个图片,同时加载图片比较耗费时间,程序不能挂起,所以使用一个线程来处理
            if(!retrieving) {
                retrieving = true;
                retrievalThread = new Thread(() -> {
                    imageIcon = new ImageIcon(imageURL, "CD Cover");
                    // 加载完毕之后,触发重新渲染,走上面的分支
                    c.repaint();
                });
                retrievalThread.start();
            }
        }
    }

    @Override
    public int getIconWidth() {
        if(imageIcon != null) {
            return imageIcon.getIconWidth();
        } else {
            return 800;
        }
    }

    @Override
    public int getIconHeight() {
        if(imageIcon != null) {
            return imageIcon.getIconHeight();
        } else {
            return 600;
        }
    }
}
