package com.zhuorui.websocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * websocket-server主启动类
 *
 * @author qinlingling
 * @date 2020/1/3
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.zhuorui")
@EnableEurekaClient
public class WebsocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketServerApplication.class, args);
    }

}



