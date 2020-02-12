package com.tim.properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Kroos.luo
 * @since 2020-02-12 22:48
 */
@RequestMapping("/properties")
@RestController
@TraceLog
public class PropertiesSourceController implements ApplicationContextAware {

    private ConfigurationBeanFactoryMetadata configurationBeanFactoryMetadata;

    @TraceLog
    @GetMapping("/")
    public void configSource() {
        TraceLog traceLog = configurationBeanFactoryMetadata.findFactoryAnnotation("propertiesSourceController", TraceLog.class);
        Map<String, Object> traceMap = configurationBeanFactoryMetadata.getBeansWithFactoryAnnotation(TraceLog.class);
        System.out.println(traceLog);
        System.out.println(traceMap);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.configurationBeanFactoryMetadata = applicationContext.getBean(
                ConfigurationBeanFactoryMetadata.BEAN_NAME,
                ConfigurationBeanFactoryMetadata.class);
    }
}
