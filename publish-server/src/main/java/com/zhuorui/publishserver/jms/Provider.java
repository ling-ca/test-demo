package com.zhuorui.publishserver.jms;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq消息生产者
 *
 * @author qinlingling
 * @date 2020/1/7
 */
@Configuration
public class Provider {

    /**
     * 创建交换机
     * @return exchange
     */
    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange("exchange");
    }

    /**
     * 创建消息队列
     * @return queue
     */
    @Bean
    public Queue queue(){
        return new Queue("queue");
    }

    /**
     * 将队列绑定到交换机
     * @return binding
     */
    @Bean
    public Binding queueBind(){
        return BindingBuilder.bind(queue()).to(exchange());
    }

}
