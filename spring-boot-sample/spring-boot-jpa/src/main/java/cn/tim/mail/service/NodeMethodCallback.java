package cn.tim.mail.service;

import cn.tim.mail.annotation.CreateNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * User: luolibing
 * Date: 2018/3/20 14:37
 */
public class NodeMethodCallback implements ReflectionUtils.MethodCallback {

    private Logger logger = LoggerFactory.getLogger(NodeMethodCallback.class);

    private ConfigurableListableBeanFactory beanFactory;
    private Object bean;
    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;

    public NodeMethodCallback(ConfigurableListableBeanFactory beanFactory, Object bean) {
        this.beanFactory = beanFactory;
        this.bean = bean;
    }

    @Override
    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

        logger.info("doWith method info :: " + String.valueOf(bean) + "." + bean.getClass().getName());
        /*
            What I expected is Printing ArtistNodeRepository Class with create Method
            But It prints something like ...

            SessionFlashMapManager
            DefaultRequestToViewNameTranslator
            ...
        */


        try {
            logger.info("When I call you :: " + method.getName()); // I expect method which contains @CreateNode annotation, but it is not ...
            Annotation[] methodAnnotations = method.getDeclaredAnnotations();
            boolean isTarget = false;
            String tid = "";
            for(Annotation anno : methodAnnotations) {
                logger.info("annotation Class :: " + anno.getClass().getName());
                if(isTarget) break;
                if(anno instanceof CreateNode) {
                    logger.info("CreateNode annotation found");
                    CreateNode createNode = method.getDeclaredAnnotation(CreateNode.class);
                    tid = createNode.tid();
                    isTarget = true;
                }
            }
            if(!isTarget) return;
            ReflectionUtils.makeAccessible(method);
        } catch (Exception e ){
            logger.error("ERROR", e);
        }
    }
}
