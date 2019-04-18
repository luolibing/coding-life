package com.tim.asm.simple;

import org.junit.Test;
import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by luolibing on 2019/4/3.
 */
public class Chapter1 {

    /**
     * asm包含有事件API，和对象API
     * 编译后的类包含几部分
     * 1 一部分类描述，例如修饰符public or private 类名，父类，接口，注解类
     * 2 字段描述，修饰符，字段名称，类型，以及字段上的注解
     * 3 方法描述，方法以及构造方法，同样有修饰符，名称返回类型，参数类型。方法上的注解，
     *
     * 源码与编译类的区别
     * 1 一个编译类只包含一个class,而源码文件可以包含多个class
     * 2 一个编译的类不包含评论
     * 3 编译的类不包含package和import部分
     *
     * 另一个很重要的不同是，编译类包含一个constant pool常量池，所有的数字，字符串，类型常亮
     *
     *
     *   Modifiers, name, super class, interfaces
         Constant pool: numeric, string and type constants
         Source file name (optional)
         Enclosing class reference
         Annotation*
         Attribute*
         Inner class* Name
         Field* Modifiers, name, type
         Annotation*
         Attribute*
         Method* Modifiers, name, return and parameter types
         Annotation*
         Attribute*
         Compiled code
     *
     *
     * 另一个重要的不同是java Type的表示方式的不同
     *
     * 类型描述，原生类型使用单个字母描述，Object 表述为Ljava/lang/Object;，int[]=[I;，Object[][]=[[Ljava/lang/Object;
     * 方法描述，void m(int i, float f)=(IF)V
     **/
    @Test
    public void test() throws IOException {

    }


    /**
     * 读取一个类
     * @throws IOException
     */
    @Test
    public void readClass() throws IOException {
        ClassReader classReader = new ClassReader(Person.class.getName());
        classReader.accept(new ClassVisitor(ASM4) {

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                System.out.println(name + " extends " + superName + "{");
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                System.out.println("    " + descriptor + " " + name);
                return null;
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.println("    " + descriptor + "  " + name);
                return null;
            }

            @Override
            public void visitEnd() {
                System.out.println("}");
            }
        }, 0);
    }


    /**
     * init方法也得写
     * 生成一个Boy extends Person {
     *     public static final int age = 100;
     *
     * }
     */
    @Test
    public void writeClass() throws IllegalAccessException, InstantiationException {
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(V1_5, ACC_PUBLIC,
                "com/tim/asm/simple/Boy", null,
                "com/tim/asm/simple/Person", null);
        // 得调用super()方法
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        // 开始着手方法体
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "com/tim/asm/simple/Person", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        classWriter.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "age", "I", null, 100).visitEnd();
        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();


        MyClassLoader myClassLoader = new MyClassLoader();
        Class clazz = myClassLoader.defineClass("com.tim.asm.simple.Boy", bytes);
        Person p = ((Person) clazz.newInstance());
        p.sayGoodBye();
    }

    public static class MyClassLoader extends ClassLoader {
        public Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}
