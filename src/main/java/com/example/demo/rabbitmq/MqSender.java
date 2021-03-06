//package com.example.demo.rabbitmq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Title: MqSender
// * Description:
// *
// */
//@Service
//@Slf4j
//public class MqSender {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    /**
//     * 默认-发送到队列
//     *
//     * @param msg
//     */
//    public void send(Object msg) {
//        log.info("发送消息：" + msg);
//        rabbitTemplate.convertAndSend("queue", msg);
//    }
//
//    /**
//     * fanout模式
//     * 将消息发送到fanoutExchange交换机
//     *
//     * @param msg
//     */
//    public void sendFanout(Object msg) {
//        log.info("发送消息：" + msg);
//        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
//    }
//
//    /**
//     * direct模式
//     *
//     * @param msg
//     */
//    public void sendDirectRed(Object msg) {
//        log.info("发送red消息：" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.red", msg);
//    }
//
//    /**
//     * direct模式
//     *
//     * @param msg
//     */
//    public void sendDirectGreen(Object msg) {
//        log.info("发送green消息：" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
//    }
//
//    /**
//     * topic模式
//     *
//     * @param msg
//     */
//    public void sendTopic01(Object msg) {
//        log.info("发送Topic01消息：" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "queue.red.message", msg);
//    }
//
//    /**
//     * topic模式
//     *
//     * @param msg
//     */
//    public void sendTopic02(Object msg) {
//        log.info("发送Topic02消息：" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "message.queue.green.abc", msg);
//    }
//
//    /**
//     * headers模式
//     *
//     * @param msg
//     */
//    public void sendHeaders01(String msg) {
//        log.info("发送Headers消息：" + msg);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("color", "red");
//        properties.setHeader("speed", "fast");
//        Message message = new Message(msg.getBytes(), properties);
//        rabbitTemplate.convertAndSend("headersExchange", "", message);
//    }
//
//    /**
//     * headers模式
//     *
//     * @param msg
//     */
//    public void sendHeaders02(String msg) {
//        log.info("发送Headers消息：" + msg);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("color", "red");
//        properties.setHeader("speed", "normal");
//        Message message = new Message(msg.getBytes(), properties);
//        rabbitTemplate.convertAndSend("headersExchange", "", message);
//    }
//
//}
