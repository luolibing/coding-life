package cn.tim.spring4.ioc722;

/**
 * Created by LuoLiBing on 16/5/19.
 */
public class TestService1 {

    private TestDao testDao;

    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }

    public void sayHello() {
        System.out.println("hello!!!!" + this.testDao.findOne());
    }
}
