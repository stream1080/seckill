package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: SeckillRabbitmqConfig
 * Description: Topic模式，秒杀
 */
@Configuration
public class SeckillRabbitmqConfig {

    /**
     * 队列 queue
     */
    public static final String SECKILL_QUEUE = "seckillQueue";

    /**
     * 交换机 exchange
     */
    public static final String SECKILL_EXCHANGE = "seckillExchange";

    /**
     * 路由键 routingkey
     * #表示0或多个单词
     * *表示一个单词
     */
    public static final String SECKILL_ROUTINGKEY = "seckill.#";

    @Bean
    public Queue secKillQueue() {
        return new Queue(SECKILL_QUEUE);
    }

    @Bean
    public DirectExchange secKillExchange() {
        return new DirectExchange(SECKILL_EXCHANGE);
    }

    @Bean
    public Binding bindingSecKill() {
        return BindingBuilder.bind(secKillQueue()).to(secKillExchange()).with(SECKILL_ROUTINGKEY);
    }

}
