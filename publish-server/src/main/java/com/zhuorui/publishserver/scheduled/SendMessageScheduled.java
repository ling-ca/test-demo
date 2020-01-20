package com.zhuorui.publishserver.scheduled;

import com.zhuorui.rediscomponent.lock.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 定时向rabbitmq消费端推送消息的服务
 *
 * @author qinlingling
 * @date 2020/1/6
 */
@Configuration
@EnableScheduling
@Component
@Slf4j
public class SendMessageScheduled {

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 定时任务：抢锁，抢到锁的服务可以将其端口号通过rabbitmq广播给websocket服务器
     * @author qinlingling
     * @date 2020/1/6
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void configureTasks() {

        try {
            String requestId = UUID.randomUUID().toString().replace("-", "");
            boolean lock = redisDistributedLock.getLock("publishLock", requestId, 60000);
            if (!lock) {
                log.info("{}未抢到锁，放弃发送消息", port);
                return;
            }
            log.info("{}抢到锁了，开始向交换机推送消息", port);
            rabbitTemplate.convertAndSend("exchange", "", port);
        } catch (Throwable e) {
            log.error("{}, 执行定时任务异常错误!", e);
        }
    }

}
