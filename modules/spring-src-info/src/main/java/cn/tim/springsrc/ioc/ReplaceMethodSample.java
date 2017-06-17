package cn.tim.springsrc.ioc;

import cn.tim.springsrc.ioc.replace.Worker;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/4/12.
 */
public class ReplaceMethodSample {

    /**
     * 动态业务替换
     * @param args
     */
    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo4.xml"));
        Worker worker = beanFactory.getBean(Worker.class);
        worker.execute(new int[] {5, 3,5, 6,4, 10, 8});
    }
}
