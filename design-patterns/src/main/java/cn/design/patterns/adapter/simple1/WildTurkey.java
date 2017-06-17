package cn.design.patterns.adapter.simple1;

/**
 * Created by LuoLiBing on 16/6/10.
 * 被适配对象Adaptee
 */
public class WildTurkey implements Turkey {

    @Override
    public void gobble() {
        System.out.println("gobble gobble");
    }

    @Override
    public void fly() {
        System.out.println("i'm flying a short distance");
    }
}
