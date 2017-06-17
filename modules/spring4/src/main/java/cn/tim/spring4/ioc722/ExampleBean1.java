package cn.tim.spring4.ioc722;

/**
 * Created by LuoLiBing on 16/5/27.
 */
public class ExampleBean1 {

    private ClientService beanOne;
    private TestDao beanTwo;
    private int i;

    private ExampleBean1(
            ClientService anotherBean, TestDao yetAnotherBean, int i) {
        this.beanOne = anotherBean;
        this.beanTwo = yetAnotherBean;
        this.i = i;
    }

    public static ExampleBean1 createInstance(ClientService anotherBean, TestDao yetAnotherBean, int i) {
        return new ExampleBean1(anotherBean, yetAnotherBean, i);
    }

}
