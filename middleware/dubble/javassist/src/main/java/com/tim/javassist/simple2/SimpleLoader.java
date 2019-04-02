package com.tim.javassist.simple2;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Created by luolibing on 2019/4/2.
 */
public class SimpleLoader extends ClassLoader {

    private ClassPool classPool;

    public SimpleLoader() throws NotFoundException {
        this.classPool = new ClassPool();
        this.classPool.insertClassPath("./class");
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            CtClass ctClass = classPool.get(name);
            byte[] bytes = ctClass.toBytecode();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }

    public static void main(String[] args) throws NotFoundException, ClassNotFoundException {
        SimpleLoader loader = new SimpleLoader();
        Class<?> clazz = loader.loadClass("com.tim.javassist.simple1.B");
    }
}
