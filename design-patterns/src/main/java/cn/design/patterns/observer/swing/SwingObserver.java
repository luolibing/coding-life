package cn.design.patterns.observer.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by LuoLiBing on 16/5/3.
 */
public class SwingObserver {

    JFrame jFrame;

    public static void main(String[] args) {
        new SwingObserver().start();
    }

    public void start() {
        jFrame = new JFrame();

        JButton button = new JButton("can i do it");

        // 添加了ActionListener 执行 actionPerformed方法
        button.addActionListener(e -> {
            System.out.println("you can't do it");
        });

        button.addActionListener( e -> {
            System.out.println("you can do it");
        });

        jFrame.getContentPane().add(BorderLayout.CENTER, button);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().add(BorderLayout.CENTER, button);
        jFrame.setSize(300,300);
        jFrame.setVisible(true);
    }
}
