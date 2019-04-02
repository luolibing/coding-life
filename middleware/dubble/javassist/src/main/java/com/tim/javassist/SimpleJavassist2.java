package com.tim.javassist;

import com.tim.javassist.simple1.B;
import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by luolibing on 2019/4/2.
 */
public class SimpleJavassist2 {


    @Test
    public void loader() throws Throwable {
        Translator translator = new Translator() {
            @Override
            public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
                System.out.println("start");
            }

            @Override
            public void onLoad(ClassPool classPool, String className) throws NotFoundException, CannotCompileException {
                System.out.println("onload " + className);
                CtClass cc = classPool.get(className);
                cc.setModifiers(Modifier.PUBLIC);
            }
        };
        ClassPool cp = ClassPool.getDefault();
        Loader loader = new Loader();
        loader.addTranslator(cp, translator);
        // 调用的是对方的main方法
        loader.run("com.tim.javassist.simple1.B", new String[0]);
    }

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, NoSuchFieldException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("java.lang.String");
        CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
        f.setModifiers(Modifier.PUBLIC);
        cc.addField(f);
        cc.writeFile(".");

        // 无法修改，抛出Prohibited package name: java.lang异常   % java -Xbootclasspath/p:. MyApp arg1 arg2...
        System.out.println(cc.toClass().getField("hiddenValue").getName());
    }

    @Test
    public void around() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.tim.javassist.simple1.B");
        CtMethod method = cc.getDeclaredMethod("setName");
        method.insertBefore("{System.out.println(\"before \" + $1);}");
        method.insertAfter("{System.out.println(\"before \" + $1);}");
        cc.writeFile();
        B b = (B) cc.toClass().newInstance();
        b.setName("luolibing");
    }

    @Test
    public void addMethod() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.tim.javassist.simple1.B");
        CtMethod method = CtNewMethod.make("public String xmove(String dx) { dx +=\"hello\"; return dx;}", cc);
        cc.addMethod(method);
        Class clazz = cc.toClass();
        Object instance = clazz.newInstance();
        Method m = clazz.getMethod("xmove", String.class);
        Object result = m.invoke(instance, "luolibing ");
        System.out.println(result);
    }
}
