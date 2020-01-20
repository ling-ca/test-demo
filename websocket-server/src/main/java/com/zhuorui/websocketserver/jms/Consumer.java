package com.zhuorui.websocketserver.jms;

import com.zhuorui.websocketserver.handler.ServerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * rabbitmq的消费端
 *
 * @author qinlingling
 * @date 2020/1/7
 */
@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = "queue")
    public void queueListener(Integer port){

        log.info("监听到消息队列， 抢到锁的端口号为{}", port);
        ServerHandler.sendMessage("端口号" + port + "抢到锁了！\n");

    }
}
