package cn.tim.thinking.innerclass.c;

import cn.tim.thinking.innerclass.a.InterfaceA;
import cn.tim.thinking.innerclass.b.OuterA;

/**
 * Created by LuoLiBing on 16/12/18.
 */
public class SubOuter extends OuterA {

    public InterfaceA interfaceA() {
        // 这个地方可以使用this.new InnerA(); 或者直接使用new InnerA(), 前提是InnerA构造函数必须是public的
        return new InnerA();
    }
}
