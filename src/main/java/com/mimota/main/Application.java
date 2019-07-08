package com.mimota.main;


import com.mimota.util.SpringBeanUtil;
import org.apache.naming.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mimota")
public class Application {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(Application.class);
        application.run();

    }
}
