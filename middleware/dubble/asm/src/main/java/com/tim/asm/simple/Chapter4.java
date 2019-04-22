package com.tim.asm.simple;

public class Chapter4 {

    /**
     * 执行模型:
     * 每个线程都有自己的执行栈，执行栈由栈帧组成，每个栈帧表示一次方法的调用
     * 栈帧由一个本地变量和一个操作数栈组成，本地变量和操作数栈的大小在代码编译阶段由方法代码计算出的结果
     * slot操作数栈保存任何java值，long和double除外， long和double需要2个slot
     */
    public void executionMode() {

    }
}
