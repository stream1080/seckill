package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitmqFanoutConfig {

    @Bean
    public Queue queue(){
        return new Queue("queue");
    }

    @Bean
    public Queue queue01(){
        return new Queue("queue_fanout01");
    }

    @Bean
    public Queue queue02(){
        return new Queue("queue_fanout02");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将队列queue01绑定到fanoutExchange交换机上
     * @return
     */
    @Bean
    public Binding binding01(){
        return BindingBuilder.bind(queue01()).to(fanoutExchange());
    }

    /**
     * 将队列queue01绑定到fanoutExchange交换机上
     * @return
     */
    @Bean
    public Binding binding02(){
        return BindingBuilder.bind(queue02()).to(fanoutExchange());
    }
}
