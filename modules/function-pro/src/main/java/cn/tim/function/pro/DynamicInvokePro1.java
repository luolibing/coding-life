package cn.tim.function.pro;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * dynamicInvoke指令
 */
public class DynamicInvokePro1 {

    public void saveClassFile() {

    }

    public void createMyClass(String clazzName) {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, clazzName, null, "java/lang/Object", null);

        // 构造方法
        MethodVisitor mcv = cw.visitMethod(ACC_PUBLIC, "", "()V", null, null);
        mcv.visitCode();
        mcv.visitVarInsn(ALOAD, 0);
        mcv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "", "()V");
        mcv.visitInsn(RETURN);
        mcv.visitMaxs(1, 1);
        mcv.visitEnd();

        MethodVisitor mainMv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mainMv.visitCode();

        mainMv.visitEnd();
    }
}
