package cn.design.patterns.template.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by LuoLiBing on 16/6/16.
 */
public class MyFrame extends JFrame {

    public MyFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setVisible(true);
    }

    /**
     * 钩子方法,只需要实现这个方法,父类会来调用这个方法
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String msg = "I rule!!";
        g.drawString(msg, 100, 100);
    }

    public static void main(String[] args) {
        new MyFrame("Head first design patterns");
    }
}
