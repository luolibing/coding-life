package cn.tim.springsrc.aop.simple;

/**
 * Created by luolibing on 2017/6/7.
 */
public class TestBean {

    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public void test() {
        System.out.println("test");
    }
}
