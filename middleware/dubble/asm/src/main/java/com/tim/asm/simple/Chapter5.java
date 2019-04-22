package com.tim.asm.simple;

import org.junit.Test;

/**
 * Created by luolibing on 2019/4/22.
 */
public class Chapter5 {

    /**
     * 执行栈
     * 每个线程都有自己的执行栈，而执行栈又由栈帧组成，栈帧代表一次方法的执行，一个栈帧由两部分组成，操作数栈和本地变量栈
     * 执行栈所有值都保存在一个slot当中，Long和double除外，他们是保留在2个slot当中
     * 字节码指令：
     * 字节码指令分两类：1 将本地变量转换成操作数栈里面的指令 2 另外一部分扮演着操作操作数栈的操作，例如pop栈计算结果，然后在Push
     * ILOAD LOAD FLOAD等等都是从本地变量栈中Load一个一个变量。 ALOAD是Load非原生变量也就是object活着数组
     * ISTORE, LSTORE, FSTORE等等是保存一个变量值到对应的本地变量
     *
     */
    @Test
    public void executionChain() {

    }


    private int age;

    /**
     * 这个简单的getAge方法的字节码是
     * ALOAD 0                  # 加载一个变量this到操作数栈
     * GETFIELD pkg/Bean f I    # 从操作数栈中获取到this，从this中获取到字段age，并且Push到操作数栈
     * IRETURN                  # 从操作数栈pop，并且将结果返回到调用者
     * @return
     */
    public int getAge() {
        return this.age;
    }

    /**
     * ALOAD 0
     * ILOAD 1                  # 加载第一个方法参数到执行栈
     * PUTFIELD pkg/Bean f I
     * RETURN
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }


    /**
     * ILOAD 1                  # 加载方法第一个参数age并且push到栈
     * IFLT label               # Pop操作数，执行判断IF LT小于0，则跳转到label
     * ALOAD 0                  # 加载this，push到栈
     * ILOAD 1                  # 加载第一个参数age到栈
     * PUTFIELD pkg/Bean f I    # 将栈的2个元素pop，然后执行PUTFIELD方法
     * GOTO end                 # 跳转到end标签
     *
     * label:
     *  NEW java/lang/IllegalArgumentException  # 初始化一个异常类
     *  DUP                     # 将其加载到栈顶
     *  INVOKESPECIAL java/lang/IllegalArgumentException <init> ()V # pop栈，获取到异常类实例，执行这个异常类的初始方法init、并Push
     *  ATHROW                  # pop将异常实例返回
     *
     * end：
     *  RETURN
     * @param age
     */
    public void checkAndSetAge(int age) {
        if(age > 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * TRYCATCHBLOCK try catch catch java/lang/InterruptedException
     * try:
     *  LLOAD 0                 # 加载第一个方法参数d，并且入栈
     *  INVOKESTATIC java/lang/Thread sleep (J)V    # pop栈获取到参数d，执行静态方法Thread.sleep方法
     *  RETURN                  # 返回结果
     *
     * catch:
     *  INVOKEVIRTUAL java/lang/InterruptedException printStackTrace ()V
     *  RETURN
     *
     * @param d
     */
    public void sleep(long d) {
        try {
            Thread.sleep(d);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
