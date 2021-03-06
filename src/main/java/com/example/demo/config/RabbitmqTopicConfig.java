package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: RabbitmqTopicConfig
 * Description: Topic模式常用
 */
@Configuration
public class RabbitmqTopicConfig {

    /**
     * 队列 queue
     */
    private static final String QUEUE_TOPIC_01 = "queue_topic01";
    private static final String QUEUE_TOPIC_02 = "queue_topic02";

    /**
     * 交换机 exchange
     */
    private static final String TOPIC_EXCHANGE = "topicExchange";

    /**
     * 路由键 routingkey
     * #表示匹配0或多个单词
     * *表示匹配一个单词
     */
    private static final String ROUTINGKEY_01 = "#.queue.#";
    private static final String ROUTINGKEY_02 = "*.queue.#";

    @Bean
    public Queue queueTopic01() {
        return new Queue(QUEUE_TOPIC_01);
    }

    @Bean
    public Queue queueTopic02() {
        return new Queue(QUEUE_TOPIC_02);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bindingTopic01() {
        return BindingBuilder.bind(queueTopic01()).to(topicExchange()).with(ROUTINGKEY_01);
    }

    @Bean
    public Binding bindingTopic02() {
        return BindingBuilder.bind(queueTopic02()).to(topicExchange()).with(ROUTINGKEY_02);
    }

}
