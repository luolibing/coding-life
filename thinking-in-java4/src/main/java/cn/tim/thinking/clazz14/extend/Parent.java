package cn.tim.thinking.clazz14.extend;

/**
 * User: luolibing
 * Date: 2017/7/5 9:55
 */
public abstract class Parent {

    private String name;

    public Parent() {
        name = this.getClass().getName();
    }

    public Parent(String name) {
        this.name = name;
    }

    public void println() {
        System.out.println(name);
    }
}
