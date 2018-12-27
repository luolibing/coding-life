package com.tim.lombok;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class CleanupSample {

    /**
     * Cleanup等同于try {} finally {} 自动close()资源，前提是close()没有参数
     * 并且close如果抛出异常，会把catch异常给覆盖掉
     */
    @Test
    @SneakyThrows
    public void execute() {
        @Cleanup InputStream in = new FileInputStream(new File("/etc/hosts"));
        int count = in.available();
        byte[] buffer = new byte[count];
        in.read(buffer);
        System.out.println(new String(buffer, "utf-8"));
    }

    class MyResource {
        public void execute() {
            System.out.println("execute");
        }

        public void dispose() {
            System.out.println("dispose");
        }
    }

    @Test
    public void resourceExecute() {
        @Cleanup("dispose") MyResource resource = new MyResource();
        resource.execute();
    }
}
