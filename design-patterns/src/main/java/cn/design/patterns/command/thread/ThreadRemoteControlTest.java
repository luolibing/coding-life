package cn.design.patterns.command.thread;

/**
 * Created by LuoLiBing on 16/6/6.
 * 队列模式,使用线程池来执行命令,可以控制命令执行的速度,流控
 */
public class ThreadRemoteControlTest {

    public static void main(String[] args) {
        ThreadRemoteControl threadRemoteControl = new ThreadRemoteControl();
        new Thread(threadRemoteControl).start();
        for(int i = 0; i < 1000; i++) {
            threadRemoteControl.push(new EchoCommand(i + " say Hello!!!" + System.currentTimeMillis()));
        }
    }
}
