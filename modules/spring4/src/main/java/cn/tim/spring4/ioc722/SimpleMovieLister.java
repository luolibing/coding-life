package cn.tim.spring4.ioc722;

/**
 * Created by LuoLiBing on 16/5/25.
 */
public class SimpleMovieLister {

    private TestDao testDao;

    /**
     * 使用set方法注入,在容器调用构造函数之后,再调用对象的set方法进行注入对象
     * @param testDao
     */
    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }
}
