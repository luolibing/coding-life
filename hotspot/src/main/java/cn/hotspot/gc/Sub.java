package cn.hotspot.gc;

/**
 * Created by LuoLiBing on 17/2/15.
 */
public class Sub extends AbstractClass {

    private int id;

    public void print() {
        System.out.println(super.getClass());
        System.out.println(super.hashCode());
    }

    public Object getSuper() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new Sub().print();
        new Sub().print();
        Object aSuper = new Sub().getSuper();
        System.out.println(aSuper);
        new MinorGC().print();
    }

    @Override
    void test1() {

    }
}


abstract class AbstractClass {
    private int age;

    public AbstractClass() {

    }

    public void test() {

    }

    abstract void test1();

    @Override
    public int hashCode() {
        return 111;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}