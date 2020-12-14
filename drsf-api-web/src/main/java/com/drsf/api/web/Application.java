package com.drsf.api.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.drsf.api")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}