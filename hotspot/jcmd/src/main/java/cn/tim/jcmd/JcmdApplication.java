package cn.tim.jcmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by luolibing on 2017/10/30.
 */
@SpringBootApplication
public class JcmdApplication {

    /**
     * $JAVA_HOME/bin下有44个指令可用
     * @param args
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("exit jcmdApplication");
        }));
        SpringApplication.run(JcmdApplication.class, args);
    }


    /**
     * jcmd 查看所有当前在执行的java指令，包括参数指令等
     * jcmd pid help
     *
     以下为可以接的参数
     VM.native_memory
     ManagementAgent.stop
     ManagementAgent.start_local
     ManagementAgent.start
     GC.rotate_log
     Thread.print
     GC.class_stats
     GC.class_histogram
     GC.heap_dump
     GC.run_finalization
     GC.run
     VM.uptime
     VM.flags
     VM.system_properties
     VM.command_line
     VM.version
     help
     * jcmd pid VM.native_memory
     *
     * jcmd PID GC.class_histogram
     *
     * jcmd PID VM.system_properties
     *
     * jcmd PID VM.flags
     *
     */
    public void jcmd() {

    }

    /**
     * jps 代替ps -aux|grep java
     * jps -lvm 查看java进程以及它的内存配置
     * java processes -love memory
     */
    public void jps()  {

    }

    /**
     * kill -3 pid && kill pid
     * 打印SIGQUIT输出并且kill掉当前的pid，正常退出
     */
    public void kill() {

    }

    /**
     * jstack pid
     * 在安全点时，线程的一个快照
     */
    public void jstack() {

    }

    /**
     * jmap
     * jmap -dump:live,format=b,file=./jmap.bin 1415
     * 打印java进程内存使用情况快照
     */
    public void jmap() {

    }

    /**
     * jhat == java head analyse tool java堆分析工具
     * jhat jmap.bin
     * http://localhost:7000/
     */
    public void jhat() {

    }

    /**
     * jinfo -flags pid
     * 输出Java进程所有的参数配置
     */
    public void jinfo() {

    }
}
