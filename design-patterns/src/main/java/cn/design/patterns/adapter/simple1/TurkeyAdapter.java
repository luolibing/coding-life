package cn.design.patterns.adapter.simple1;

/**
 * Created by LuoLiBing on 16/6/10.
 * 鸭子不够用火鸡来代替,但是火鸡接口和鸭子接口不一样,需要使用一个适配器来进行转换
 * 适配器:
 * 适配器需要持有一个被适配的对象,同时实现目标接口,接口方法可以通过被适配对象来进行实现
 *
 * 适配器模式
 * 将一个类的接口,转换成客户期望的另一个接口,适配器让原本接口不兼容的类可以合作无间
 *
 * Adapter适配器
 */
public class TurkeyAdapter implements Duck {

    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        for(int i = 0; i < 5; i++) {
            turkey.fly();
        }
    }
}
