package cn.tim.function.pro;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.objectweb.asm.Opcodes.*;

/**
 * dynamicInvoke指令
 * http://www.infoq.com/cn/articles/Invokedynamic-Javas-secret-weapon
 */
public class DynamicInvokePro1 {


    public static void main(String[] args) throws IOException {
        final String outputClassName = "cn/tim/function/pro/Dynamic";
        String basePath = DynamicInvokePro1.class.getClassLoader().getResource("").getPath();
        Path classPath = Paths.get(basePath, outputClassName + ".class");
        if(!Files.exists(classPath)) {
            Files.createFile(classPath);
        }
        try (FileOutputStream fos
                     = new FileOutputStream(classPath.toFile())) {
            fos.write(createMyClass(outputClassName));
        }
    }

    // 生成类文件的byte数组
    public static byte[] createMyClass(String clazzName) {
        // 构造默认构造函数
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

        // 构造main方法
        MethodVisitor mainMv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mainMv.visitCode();
        MethodType mt = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
                MethodType.class);
        // 找到bootstrap方法，并且传入对应的参数
        Handle bootstrap = new Handle(Opcodes.H_INVOKESTATIC, "cn/tim/function/pro/DynamicInvokePro1", "bootstrap",
                mt.toMethodDescriptorString());
        // 让invokeDynamic关联一个对应的方法句柄，当遇到dynamic指令时，调用对应的bsm方法。
        mainMv.visitInvokeDynamicInsn("runDynamic", "()V", bootstrap);
        mainMv.visitInsn(RETURN);
        mainMv.visitMaxs(0, 1);
        mainMv.visitEnd();
        cw.visitEnd();
        return cw.toByteArray();
    }

    public static void sayHello() {
        System.out.println("hahaha luolibing");
    }

    // bsm方法，返回一个调用点，包含methodHandle和具体要调用的方法
    public static CallSite bootstrap(MethodHandles.Lookup caller, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {
        System.out.println("caller:" + caller + ", name:" + name);
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 需要使用lookupClass()，因为这个方法是静态的
        final Class currentClass = lookup.lookupClass();
        // 返回类型
        final MethodType targetSignature = MethodType.methodType(void.class);
        // 查找对应的静态方法
        final MethodHandle targetMH = lookup.findStatic(currentClass, "sayHello", targetSignature);
        // 构造出一个调用点
        return new ConstantCallSite(targetMH.asType(type));
    }
}
