package com.tim.asm.simple;

import org.junit.Test;
import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.V1_5;

public class Chapter2 {

    /**
     * 类的简单拷贝
     * @throws IOException
     */
    @Test
    public void transformCopy() throws IOException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("com/tim/asm/simple/Person.class");
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(0);
        cr.accept(cw, 0);

        byte[] output = cw.toByteArray();
        Files.write(Paths.get("/p.class"), output);
        byte[] bytes = Files.readAllBytes(Paths.get(getBaseClassPath() + "com/tim/asm/simple/p.class"));
        cr = new ClassReader(bytes);
        String className = cr.getClassName();
        System.out.println(className);
    }

    @Test
    public void filter() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("com/tim/asm/simple/Person.class");
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new ChangeClassAdapter(cw);
        cr.accept(cv, 0);

        byte[] output = cw.toByteArray();

        Files.write(Paths.get(getBaseClassPath() + cr.getClassName() + "$Proxy.class"), output);

        inputStream = ClassLoader.getSystemResourceAsStream(cr.getClassName() + "$Proxy.class");
        cr = new ClassReader(inputStream);
        System.out.println(cr.getClassName());
        // 不能使用/
        Class<?> clazz = new Chapter1.MyClassLoader().defineClass("com.tim.asm.simple.Person$Proxy", output);
        Person p = (Person) clazz.newInstance();
        p.sayGoodBye();
    }


    /**
     * 如果加代理怎么加，一种方式是组合，代理对象拥有一个target，还有一种方式是继承
     */
    public static class ChangeClassAdapter extends ClassVisitor {

        public ChangeClassAdapter(ClassWriter cw) {
            super(ASM4, cw);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            // 改变类名，改变版本号，继承当前类
            super.visit(V1_5, access, name + "$Proxy", signature, name, new String[0]);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            return super.visitAnnotation(descriptor, visible);
        }
    }

    private static String getBaseClassPath() {
        // 获取当前classPath目录
        String file = Chapter2.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        return file;
    }
}
