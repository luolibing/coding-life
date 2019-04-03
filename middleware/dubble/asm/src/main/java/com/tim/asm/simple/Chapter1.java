package com.tim.asm.simple;

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
    public void test() {

    }
}
