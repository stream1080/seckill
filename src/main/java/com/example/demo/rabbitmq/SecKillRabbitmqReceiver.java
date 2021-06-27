package com.example.demo.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.SeckillRabbitmqConfig;
import com.example.demo.entity.Order;
import com.example.demo.entity.SeckillOrder;
import com.example.demo.entity.User;
import com.example.demo.service.GoodsService;
import com.example.demo.service.OrderService;
import com.example.demo.service.SeckillOrderService;
import com.example.demo.vo.GoodsVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Title: MqReceiver
 * Description:
 *
 */
@Service
@Slf4j
public class SecKillRabbitmqReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderService orderService;

    private Gson gson = new Gson();


    /**
     * 接收秒杀消息
     *
     * @param msg
     */
    @RabbitListener(queues = SeckillRabbitmqConfig.SECKILL_QUEUE)
    public void receiverSeckillMessage(String msg) {
//        SeckillMessage seckillMessage = JSON.parseObject(msg, SeckillMessage.class);
        SeckillMessage seckillMessage = gson.fromJson(msg,SeckillMessage.class);
        log.info(SeckillRabbitmqConfig.SECKILL_QUEUE + "接收消息：" + seckillMessage);
        User user = seckillMessage.getUser();
        Long goodsId = seckillMessage.getGoodsId();

        //判断库存是否足够
        GoodsVo goods = goodsService.findGoodsVoById(goodsId);
        if (goods.getStockCount() < 1) {
            return;
        }

        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrder != null) {
            return;
        }

        //秒杀操作：减库存，下订单，写入秒杀订单
        Order order = orderService.seckill(user, goods);
    }

}