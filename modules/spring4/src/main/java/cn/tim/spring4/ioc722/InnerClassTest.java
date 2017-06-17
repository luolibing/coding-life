package cn.tim.spring4.ioc722;

/**
 * Created by LuoLiBing on 16/5/27.
 */
public class InnerClassTest {

    private InnerClass target;

    public void setTarget(InnerClass target) {
        this.target = target;
    }

    public void execute() {
        target.sayHello();
    }
}
