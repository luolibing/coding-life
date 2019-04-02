package com.tim.javassist;

import com.tim.javassist.simple1.A;
import com.tim.javassist.simple1.B;
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
        B a = (B) clazz.newInstance();
        a.sayHello();

        pool.appendSystemPath();

        // 分离，脱离
        ctClass.detach();
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

    @Test
    public void rename() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.tim.javassist.simple1.A");
        ctClass.setName("com.tim.javassist.simple1.D");
        ctClass.writeFile("/Users/luolibing/Documents/github/coding-life/middleware/dubble/javassist/target/classes/com/tim/javassist");
    }

    @Test
    public void modifyMethod() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.tim.javassist.simple1.B");
        CtMethod cmethod = ctClass.getDeclaredMethod("sayHello");

        // 切面
        cmethod.insertBefore("{System.out.println(\"before B sayHello\");}");
        cmethod.insertAfter("{System.out.println(\"after B sayHello\");}");
        Class clazz = ctClass.toClass();
        B instance = (B) clazz.newInstance();
        instance.sayHello();
    }

    @Test
    public void loader() throws NotFoundException, CannotCompileException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();
        Loader loader = new Loader(pool);
        CtClass ctClass = pool.get("com.tim.javassist.simple1.A");
        ctClass.setSuperclass(pool.get("com.tim.javassist.simple1.B"));
        Class clazz = loader.loadClass("com.tim.javassist.simple1.A");
        Object a = clazz.newInstance();
        Method method = clazz.getMethod("sayHello");
        method.invoke(a);
        System.out.println(a);
    }
}
