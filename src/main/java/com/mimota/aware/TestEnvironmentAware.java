package com.mimota.aware;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//@Component
public class TestEnvironmentAware implements EnvironmentAware {
    public void setEnvironment(Environment environment) {
        System.out.println("environment aware");
    }
}
