package cn.design.patterns.mvc.dj;

import javax.swing.*;
import java.awt.*;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public class BeatBar extends JProgressBar implements Runnable {
    private static final long serialVersionUID = 2L;
    JProgressBar progressBar;
    Thread thread;

    public BeatBar() {
        thread = new Thread(this);
        setMaximum(100);
        setBackground(Color.BLACK);
        thread.start();
    }

    public void run() {
        for(;;) {
            int value = getValue();
            value = (int)(value * .75);
            setValue(value);
            repaint();
            try {
                Thread.sleep(50);
            } catch (Exception e) {};
        }
    }
}
