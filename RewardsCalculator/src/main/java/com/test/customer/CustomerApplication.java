package com.test.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
"com.test.customer" })
@ComponentScan(basePackages="com.test.customer") 
@EnableScheduling
@EnableConfigurationProperties
public class CustomerApplication extends SpringBootServletInitializer {

    public static void main(final String... args) {
        final SpringApplication app = new SpringApplication(CustomerApplication.class);
        app.run(args);
    }
}
