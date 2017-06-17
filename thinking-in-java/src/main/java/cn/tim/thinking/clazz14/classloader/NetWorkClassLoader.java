package cn.tim.thinking.clazz14.classloader;

import cn.tim.thinking.clazz14.CastClassTest;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LuoLiBing on 17/3/1.
 */
public class NetWorkClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getClassBytes(name);
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] getClassBytes(String name) {
        try(InputStream in = this.getClass().getClassLoader().getResourceAsStream(name)) {
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for(int i = 0; i < 10; i++) {
            Class<?> clazz = new NetWorkClassLoader().loadClass("cn.tim.thinking.clazz14.CastClassTest");
            System.out.println(clazz);
            CastClassTest o = (CastClassTest) clazz.newInstance();
            o.test1();
        }
    }
}
