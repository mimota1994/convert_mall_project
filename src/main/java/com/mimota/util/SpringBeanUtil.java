package com.mimota.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public SpringBeanUtil() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }

    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getContext().getBean(name, clazz);
    }

//    public static <T> T getbean(String name) {
//        return getContext().getBean(name);
//    }

    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }
}
