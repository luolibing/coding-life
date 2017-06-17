package cn.tim.counter.queue;

import cn.tim.counter.entity.Loger;
import cn.tim.counter.entity.jpa.Logdao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by LuoLiBing on 16/10/20.
 */
public class AccessTaskQueue implements Runnable {

    private String name;

    private Logdao logdao;

    private BlockingQueue<Loger> queue;

    private List<Loger> list;

    private final static int THRESHOLD = 100;

    public AccessTaskQueue(String name, Logdao logdao) {
        this.name = name;
        this.logdao = logdao;
        queue = new LinkedBlockingQueue<>();
        list = new ArrayList<>(100);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Loger loger = queue.take();
                list.add(loger);
                if (list.size() >= THRESHOLD) {
                    saveAndClear();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(Loger loger) {
        try {
            queue.put(loger);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveAndClear() {
        // 批量保存 logdao.updatelog(list);
        list.clear();
    }
}
