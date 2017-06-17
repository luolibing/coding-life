package cn.design.patterns.command.thread;

import cn.design.patterns.command.simple2.Command;

/**
 * Created by LuoLiBing on 16/6/6.
 * 队列请求,一个一个的命令发送到一个队列中,然后使用一个线程池来执行命令
 * 已达到控制流程的效果,当线程完成之后提交到线程池
 *
 */
public class EchoCommand implements Command {

    private String message;

    public EchoCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("message:" + message);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {

    }
}
