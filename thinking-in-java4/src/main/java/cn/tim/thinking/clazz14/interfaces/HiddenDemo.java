package cn.tim.thinking.clazz14.interfaces;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public class HiddenDemo {

    public static void main(String[] args) {
        InterfaceDemo.A a = HiddenC.makeA();
        a.f();

        // 防止强转的一种方式是,使用包权限,即使知道是某种类型也不能在包外对其进行判断,然后进行转型
        if(a instanceof C) {

        }
    }
}
