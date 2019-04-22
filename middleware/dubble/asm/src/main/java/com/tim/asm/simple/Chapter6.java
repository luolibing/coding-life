package com.tim.asm.simple;

import org.junit.Test;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by luolibing on 2019/4/22.
 */
public class Chapter6 {

    @Test
    public void generateMethod1() throws IllegalAccessException, InstantiationException {
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(V1_5, ACC_PUBLIC,
                "com/tim/asm/simple/Person", null,
                "java/lang/Object", null);

        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "getScore", "()I", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "com/tim/asm/simple/Person", "score", "I");
        mv.visitInsn(IRETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        classWriter.visitEnd();

        byte[] bytes = classWriter.toByteArray();


        Chapter1.MyClassLoader myClassLoader = new Chapter1.MyClassLoader();
        Class clazz = myClassLoader.defineClass("com.tim.asm.simple.Person", bytes);
        Person p = ((Person) clazz.newInstance());
        System.out.println(p);
    }

    public static class RemoveNopAdapter extends MethodVisitor {

        public RemoveNopAdapter(MethodVisitor mv) {
            super(ASM4, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            if(opcode != NOP) {
                mv.visitInsn(opcode);
            }
        }
    }

    public static class RemoveNopClassAdapter extends ClassVisitor {
        public RemoveNopClassAdapter(ClassVisitor cv) {
            super(ASM4, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            if(mv != null) {
                mv = new RemoveNopAdapter(mv);
            }
            return mv;
        }
    }
}
