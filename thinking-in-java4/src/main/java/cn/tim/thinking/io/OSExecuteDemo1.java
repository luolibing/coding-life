package cn.tim.thinking.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by LuoLiBing on 16/12/5.
 *
 * 进程控制
 *
 */
public class OSExecuteDemo1 {

    static class OSExecuteException extends RuntimeException {
        public OSExecuteException(String message) {
            super(message);
        }
    }

    public static class OSExecute {
        public static void command(String command) {
            boolean err = false;
            try {
                // 使用ProcessBuilder产生一个进程
                Process process = new ProcessBuilder(command.split(" ")).start();
                // 将进程产生的输出read出来
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                }

                // 重定向进程的错误输出, 程序进程自身执行过程中产生的错误
                BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((s = errors.readLine()) != null) {
                    System.err.println("error= " + s);
                    err = true;
                }
                // 普通的导致异常的错误,例如错误的命令等
            } catch (IOException e) {
                if(command.startsWith("CMD /C")) {
                    command("CMD /C " + command);
                } else {
                    throw new RuntimeException(e);
                }
            }

            if(err) {
                throw new OSExecuteException("Errors executing" + command);
            }
        }
    }


    static class OSExecute2 extends ArrayList<String> {
        public void command(String command) {
            boolean err = false;
            try {
                // 使用ProcessBuilder产生一个进程
                Process process = new ProcessBuilder(command.split(" ")).start();
                // 将进程产生的输出read出来
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String s;
                while ((s = reader.readLine()) != null) {
                    add(s);
                }

                // 重定向进程的错误输出, 程序进程自身执行过程中产生的错误
                BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((s = errors.readLine()) != null) {
                    System.err.println("error= " + s);
                    err = true;
                }
                // 普通的导致异常的错误,例如错误的命令等
            } catch (IOException e) {
                if(command.startsWith("CMD /C")) {
                    command("CMD /C " + command);
                } else {
                    throw new RuntimeException(e);
                }
            }

            if(err) {
                throw new OSExecuteException("Errors executing" + command);
            }
        }
    }
}
