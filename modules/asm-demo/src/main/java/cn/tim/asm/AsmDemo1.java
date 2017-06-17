package cn.tim.asm;

import org.junit.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by luolibing on 2017/3/25.
 * ASM是一个java字节码操纵框架，它被用来动态生成类或者增强既有类的功能。 ASM可以直接产生二进制class文件，也可以在类加载入JVM之前动态更改类的行为。
 * 这些类文件拥有足够的元数据来解析类中所有元素：类名称、方法、属性以及字节码指令。 ASM从类文件中读入信息后，能够改变类行为，分析类信息，甚至能够根据用户要求生产新类。
 *
 * ASM核心类
 * ClassReader      用来解析编译过的class字节码文件
 * ClassWriter      用来重新构建编译后的类
 * ClassAdapter     该类也事先了ClassVistor借口
 */
public class AsmDemo1 {

    @Test
    public void write() throws IOException {
        ClassWriter writer = new ClassWriter(0);
        // public interface AsmDemo2 extend Serializable定义类头信息
        writer.visit(
                Opcodes.V1_5,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
                "cn/tim/asm/AsmDemo2",
                null,
                "java/lang/Object",
                new String[] {"java.util.Serializable"});
        // 定义类的属性 public final static Integer DEFAULT_NAME = -1; visitEnd()表明读取结束
        writer.visitField(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL,
                "DEFAULT_NAME", "I", null, -1
                ).visitEnd();
        // 定义类的方法 public final static void sayHello(String name) {}
        writer.visitMethod(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT,
                // (Ljava/lang/String;)V 表示参数为（string）返回值为void
                "sayHello", "(Ljava/lang/String;)V", null, null
                ).visitEnd();
        // 完成类的写入
        writer.visitEnd();
        // 将写入的类转换为字节数组，并且写入到对应的class文件当中。
        byte[] data = writer.toByteArray();
        Path path = Paths.get("./AsmDemo2.class");
        Files.write(path, data);
    }
}
