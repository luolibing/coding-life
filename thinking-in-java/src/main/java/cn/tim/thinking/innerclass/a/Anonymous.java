package cn.tim.thinking.innerclass.a;

/**
 * Created by LuoLiBing on 16/12/21.
 */
public class Anonymous {

    public InterfaceA interfaceA() {
        return new InterfaceA() {
            @Override
            public void f() {

            }
        };
    }
}
