package cn.tim.springsrc.ioc.dependency;

/**
 * Created by luolibing on 2017/5/11.
 */
public class TestC {

    private TestA testA;

    public TestC() {
    }

    public TestC(TestA testA) {
        this.testA = testA;
    }

    public void c() {
        testA.a();
    }

    public TestA getTestA() {
        return testA;
    }

    public void setTestA(TestA testA) {
        this.testA = testA;
    }
}
