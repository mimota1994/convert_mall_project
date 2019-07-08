package com.mimota.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


//@Component
public class TestApplicationContextAware implements ApplicationContextAware {
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("application context aware");
    }
}
