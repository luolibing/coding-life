package cn.tim.validate;

import cn.tim.validate.filter.CORSFilter;
import cn.tim.validate.filter.UpgradeFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

/**
 * Created by LuoLiBing on 15/10/8.
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        for(String beanName: beanNames) {
            System.out.println(beanName);
        }
    }

    /**
     * 原来表单提交只支持get与post方式,为了能支持其他请求方式
     * 可以添加一个HiddenHttpMethodFilter来将post转换为对应的请求方式
     * @return
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    /**
     * etag 过滤器,自动添加上etag response header
     * etag 值等于response的byte[] inputstream取的checksum校验和,
     * requestETag请求的时候会每次都携带这个ETag过来,
     * 然后与reponse的进行比较,如果一样则返回304 not modified
     * @return
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    /**
     * cors 跨域问题
     * @return
     */
    @Bean
    public Filter corsFilter1() {
        return new CORSFilter();
    }

    /**
     * 编码
     * @return
     */
    @Bean
    public Filter characterEncodingFilter() {
        return new CharacterEncodingFilter("UTF-8", true);
    }

    @Bean
    public Filter upgradeFilter() {
        return new UpgradeFilter();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/notfound").setViewName("forward: index.html");
    }

    /**
     * 定义
     * @return
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean corsFilterBean = new FilterRegistrationBean();
        CORSFilter corsFilter = new CORSFilter();
        corsFilterBean.setOrder(1);
        corsFilterBean.setFilter(corsFilter);
        return corsFilterBean;
    }

}
