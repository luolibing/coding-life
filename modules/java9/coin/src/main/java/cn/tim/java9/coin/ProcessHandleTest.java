package cn.tim.java9.coin;

import org.junit.Test;

import java.io.IOException;

/**
 * User: luolibing
 * Date: 2017/9/25 15:31
 */
public class ProcessHandleTest {

    @Test
    public void processHandle() throws IOException, InterruptedException {
        // 当前进程
        ProcessHandle currentProcess = ProcessHandle.current();
        // 所有进程列表
        ProcessHandle.allProcesses()
                .filter(processHandle -> processHandle.pid() != currentProcess.pid())
                .forEach(processHandle -> {
                    System.out.println(processHandle);
                    // 进程详情
                    ProcessHandle.Info info = processHandle.info();
                    info.command().ifPresent(System.out::println);
                    // 监听进程退出事件，不能监控当前的进程
                    processHandle.onExit()
                        .thenRun(() -> System.out.format("进程%s关闭了\n", processHandle.pid()));
                });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
