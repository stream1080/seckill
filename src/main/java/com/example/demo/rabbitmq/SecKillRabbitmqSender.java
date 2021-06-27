package com.example.demo.rabbitmq;

import com.example.demo.config.SeckillRabbitmqConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Title: SecKillRabbitmqSender
 * Description:
 *
 */
@Service
@Slf4j
public class SecKillRabbitmqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Gson gson = new Gson();

    /**
     * 发送秒杀消息
     *
     * @param seckillMessage
     */
    public void sendSeckillMessage(SeckillMessage seckillMessage) {
        log.info("发送seckill消息：" + seckillMessage);
        //消息队列可以发送 字符串、字节数组、序列化对象
//        String msg = JSON.toJSONString(seckillMessage);
        String msg = gson.toJson(seckillMessage);
        rabbitTemplate.convertAndSend(SeckillRabbitmqConfig.SECKILL_EXCHANGE, SeckillRabbitmqConfig.SECKILL_ROUTINGKEY, msg);
    }

}
