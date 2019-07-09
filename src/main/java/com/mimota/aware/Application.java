package com.mimota.aware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.mimota.aware", lazyInit = true)
public class Application {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(com.mimota.main.Application.class);
        application.run();

    }
}
