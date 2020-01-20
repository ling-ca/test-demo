package com.zhuorui.publishserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * rabbitmq生产者的启动类
 *
 * @author qinlingling
 * @date 2020/1/6
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.zhuorui")
@EnableEurekaClient
public class PublishServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishServerApplication.class, args);
    }

}
