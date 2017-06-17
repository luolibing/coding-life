package cn.tim.quartz.database.utils;

import org.springframework.context.ApplicationContext;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public class ApplicationContextProvider {

    private static ApplicationContext applicationContext;


    public static void createInstance(ApplicationContext applicationContext) {
        if(ApplicationContextProvider.applicationContext == null) {
            synchronized (ApplicationContextProvider.class) {
                if(ApplicationContextProvider.applicationContext == null) {
                    ApplicationContextProvider.applicationContext = applicationContext;
                }
            }
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        if(applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }
}
