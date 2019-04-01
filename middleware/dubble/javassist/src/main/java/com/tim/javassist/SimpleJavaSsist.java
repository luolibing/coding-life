package com.tim.javassist;

import com.tim.javassist.simple1.A;
import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by luolibing on 2019/4/1.
 */
public class SimpleJavaSsist {

    @Test
    public void modifyClass() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 有一个hashTable来存储对应的类
        ClassPool pool = ClassPool.getDefault();
        // CtClass代表一个抽象的类文件
        CtClass ctClass = pool.get("com.tim.javassist.simple1.A");
        ctClass.setSuperclass(pool.get("com.tim.javassist.simple1.B"));
        ctClass.writeFile();

        // 可以通过ctClass.toClass()获取到生成后的对象
        Class clazz = ctClass.toClass();
        A a = (A) clazz.newInstance();
        Method sayHello = clazz.getMethod("sayHello");
        sayHello.invoke(a);
    }

    @Test
    public void defineClass() throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass cClass = classPool.makeClass("com.tim.javassist.simple1.C");
        cClass.writeFile("/Users/luolibing/Documents/github/coding-life/middleware/dubble/javassist/target/classes/com/tim/javassist");

        // 已经冻结
        try {
            cClass.setSuperclass(classPool.get("com.tim.javassist.simple1.B"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解冻
        cClass.defrost();
        cClass.setSuperclass(classPool.get("com.tim.javassist.simple1.B"));
    }
}
