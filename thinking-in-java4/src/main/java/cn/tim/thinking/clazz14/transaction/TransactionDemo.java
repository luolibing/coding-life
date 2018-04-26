package cn.tim.thinking.clazz14.transaction;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public class TransactionDemo {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(Paths.get("/share/output.log"));
        Handler proxy = (Handler) Proxy.newProxyInstance(
                Handler.class.getClassLoader(),
                new Class[] {Handler.class},
                new FileTransaction(fileHandler)
        );
        try {
            proxy.append("aaaaaaaaaa");
            proxy.append("bbbbbbbbbb");
            proxy.append("cccccccccc");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
