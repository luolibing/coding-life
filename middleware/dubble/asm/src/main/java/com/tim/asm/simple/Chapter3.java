package com.tim.asm.simple;

import org.junit.Test;
import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static org.objectweb.asm.Opcodes.ASM4;

public class Chapter3 {

    @Test
    public void clean() throws IOException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("com/tim/asm/simple/Person.class");
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new CleaningClassAdapter(cw);
        cr.accept(cv, 0);

        byte[] output = cw.toByteArray();

        Files.write(Paths.get(getBaseClassPath() + cr.getClassName() + "$Proxy.class"), output);

        inputStream = ClassLoader.getSystemResourceAsStream(cr.getClassName() + "$Proxy.class");
        cr = new ClassReader(inputStream);
        System.out.println(cr.getClassName());
        // 不能使用/
        Class<?> clazz = new Chapter1.MyClassLoader().defineClass("com.tim.asm.simple.Person$Proxy", output);
        Method[] methods = clazz.getMethods();
        Stream.of(methods)
                .forEach(System.out::println);
    }

    private static Set<String> retainMethod = Collections.singleton("sayGoodBye");

    public static class CleaningClassAdapter extends ClassVisitor {

        public CleaningClassAdapter(ClassVisitor classVisitor) {
            super(ASM4, classVisitor);
        }

        @Override
        public void visitSource(String source, String debug) {

        }

        @Override
        public void visitOuterClass(String owner, String name, String descriptor) {

        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {

        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            return null;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if(!retainMethod.contains(name)) {
                return null;
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name + "$Proxy", signature, "java/lang/Object", new String[0]);
        }
    }

    private static String getBaseClassPath() {
        // 获取当前classPath目录
        String file = Chapter2.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        return file;
    }

    @Test
    public void addFields() {

    }

    public static class AddFieldClassVisitor extends ClassVisitor {

        private int acc;

        private String fieldName;

        private String fdesc;

        private boolean exists = false;

        private ClassVisitor cv;

        public AddFieldClassVisitor(int acc, String fieldName, String fdesc, ClassVisitor classVisitor) {
            super(ASM4, classVisitor);
            this.fieldName = fieldName;
            this.acc = acc;
            this.fdesc = fdesc;
            this.cv = classVisitor;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            if(Objects.equals(name, fieldName)) {
                exists = true;
            }
            return super.visitField(access, name, descriptor, signature, value);
        }

        @Override
        public void visitEnd() {
            if(!exists) {
                MethodVisitor mv = cv.visitMethod(acc, fieldName, fdesc, null, null);
                mv.visitEnd();
            }
            cv.visitEnd();
        }
    }
}
