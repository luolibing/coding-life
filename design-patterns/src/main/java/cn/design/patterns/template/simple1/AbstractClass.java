package cn.design.patterns.template.simple1;

/**
 * Created by LuoLiBing on 16/6/12.
 */
public abstract class AbstractClass {

    final void templateMethod() {

    }

    abstract void primitiveOperation1();

    abstract void primitiveOperation2();

    final void concreteOperation() {}

    void hook() {}
}
