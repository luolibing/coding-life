package cn.tim.quartz.database.config;

import cn.tim.quartz.database.job.JobFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        JobDetail jobDetail = bundle.getJobDetail();
        Job job = JobFactory.createCallbackJob(jobDetail.getJobDataMap());
//        Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }
}
