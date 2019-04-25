package com.tim.asm.simple;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * Created by luolibing on 2019/4/22.
 */
public class Chapter7 {

    /**
     * 如何用asm实现简单的aop
     */
    @Test
    public void howAopWork() throws Exception {
        ClassReader cr = new ClassReader("com.tim.asm.simple.Worker");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classAdapter = new LogClassAdapter(cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        String baseClassPath = getBaseClassPath();
        File file = Paths.get(baseClassPath, "com/tim/asm/simple/Worker.class").toFile();
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();


        Worker worker = new Worker();
        worker.execute("aaaa");
    }

    public static class LogClassAdapter extends ClassVisitor {

        private ClassVisitor cv;

        public LogClassAdapter(ClassVisitor cv) {
            super(ASM4);
            this.cv = cv;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            if(mv != null) {
                return new LogMethodAdapter(mv);
            }
            return mv;
        }
    }

    public static class LogMethodAdapter extends MethodVisitor {

        private MethodVisitor mv;

        public LogMethodAdapter(MethodVisitor mv) {
            super(ASM4);
            this.mv = mv;
        }

        @Override
        public void visitCode() {
            mv.visitMethodInsn(INVOKESTATIC, "com/tim/asm/simple/Logger", "info", "()V", false);
//            super.visitCode();
        }
    }

    private static String getBaseClassPath() {
        // 获取当前classPath目录
        String file = Chapter7.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        return file;
    }
}
