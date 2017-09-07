package cn.tim.classloader;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by LuoLiBing on 15/8/10.
 */
public class Test1 {

    public static void main(String[] args) throws IOException {
        Enumeration<URL> urlEnumeration = ClassLoader.getSystemResources("META-INF/spring.factories");
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            System.out.println(url.getPath());
        }
        sayHello();
    }

    public static void sayHello() {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for(StackTraceElement element:stackTrace) {
            System.out.println(element.getClassName());
        }
    }

    @Test
    public void stack() {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for(StackTraceElement stack: stackTrace) {
            System.out.println(stack);
        }
    }

    @Test
    public void threadClassload() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
    }

}
