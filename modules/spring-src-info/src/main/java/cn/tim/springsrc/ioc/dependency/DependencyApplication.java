package cn.tim.springsrc.ioc.dependency;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/5/15.
 */
public class DependencyApplication {

    /**
     * 通过构造函数循环依赖是会出问题的，会抛出BeanCurrentlyInCreationException异常.
     * 循环依赖探测使用一个当前加载的Bean的Map,然后在加载的时候，查看当前加载的Bean是否在Map中，如果在，则表明是循环依赖。
     */
    @Test
    public void construct() {
        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo9.xml"));
        TestA bean = factory.getBean(TestA.class);
        bean.a();
    }


    /**
     * set方法循环依赖依赖.
     * 由于将new和set依赖分开，所以可以在new之后，还没有set的时候就将其提前暴露给ObjectFactory,
     * 这样当遇到循环依赖时，直接返回还未完全set的bean。从而解决了循环依赖，但是这个仅限于单例singleton
     * 因为prototype不缓存已经加载的Bean,所以无法解决循环依赖。
     *
     * 只有单例&允许循环依赖&当前bean正在创建中，才允许提前曝光
     */
    @Test
    public void set() {
        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo10.xml"));
        TestA bean = factory.getBean(TestA.class);
        System.out.println(bean);
        /**
         * 调用循环依赖，这个没有办法，肯定会造成死循环。
         */
//        bean.a();
    }

}
