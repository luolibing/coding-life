package cn.design.patterns.command.thread;

import cn.design.patterns.command.simple2.Command;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/6/6.
 */
public class ThreadRemoteControl implements Runnable {

    private LinkedTransferQueue<Command> commandQueue;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public ThreadRemoteControl() {
        commandQueue = new LinkedTransferQueue<>();
    }

    public void push(Command command) {
        try {
            commandQueue.transfer(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Command command = commandQueue.take();
                executor.submit((Runnable) command::execute);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
