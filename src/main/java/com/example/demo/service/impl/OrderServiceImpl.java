package com.example.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.*;
import com.example.demo.exception.GlobalException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.GoodsService;
import com.example.demo.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.service.SeckillGoodsService;
import com.example.demo.service.SeckillOrderService;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.OrderDetailVo;
import com.example.demo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 秒杀-订单 服务实现类
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private RedisTemplate redisTemplate;

//    /**
//     * 秒杀-存在库存超卖问题
//     * @param user
//     * @param goods
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Order seckill(User user, GoodsVo goods) {
//        //秒杀商品表减库存
//        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
//        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
//        seckillGoodsService.updateById(seckillGoods);
//
//        //生成订单
//        Order order = new Order();
//        order.setUserId(user.getId());
//        order.setGoodsId(goods.getId());
//        order.setDeliveryAddrId(0L);
//        order.setGoodsName(goods.getGoodsName());
//        order.setGoodsCount(1);
//        order.setGoodsPrice(goods.getSeckillPrice());
//        order.setOrderChannel(1);
//        order.setStatus(0);
//        order.setCreateDate(new Date());
//        orderService.save(order);
//
//        //生成秒杀订单
//        SeckillOrder seckillOrder = new SeckillOrder();
//        seckillOrder.setUserId(user.getId());
//        seckillOrder.setOrderId(order.getId());
//        seckillOrder.setGoodsId(goods.getId());
//        seckillOrderService.save(seckillOrder);
//
//        return order;
//    }

//    /**
//     * 秒杀 解决超卖问题
//     *
//     * @param user
//     * @param goods
//     * @return
//     */
//    @Override
//    @Transactional
//    public Order seckill(User user, GoodsVo goods) {
//        //秒杀商品表减库存
//        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
//        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
//
////        boolean result = seckillGoodsService.update(
////        new UpdateWrapper<SeckillGoods>().setSql(
////        "stock_count=" + "stock_count-1").eq("goods_id",
////        seckillGoods.getId()).gt("stock_count", 0));
//
//        boolean result = seckillGoodsService.update(seckillGoods,new UpdateWrapper<SeckillGoods>().setSql(
//                "stock_count=stock_count-1").eq("goods_id",
//                seckillGoods.getId()).gt("stock_count",0));
//
//        if (!result) {
//            //减库存失败，商品卖完了
////            setGoodsOver(goods.getId());
//            return null;
//        }
//
//        //生成订单
//        Order order = new Order();
//        order.setUserId(user.getId());
//        order.setGoodsId(goods.getId());
//        order.setDeliveryAddrId(0L);
//        order.setGoodsName(goods.getGoodsName());
//        order.setGoodsCount(1);
//        order.setGoodsPrice(goods.getSeckillPrice());
//        order.setOrderChannel(1);
//        order.setStatus(0);
//        order.setCreateDate(new Date());
//        orderService.save(order);
//
//        //生成秒杀订单
//        SeckillOrder seckillOrder = new SeckillOrder();
//        seckillOrder.setUserId(user.getId());
//        seckillOrder.setOrderId(order.getId());
//        seckillOrder.setGoodsId(goods.getId());
//        seckillOrderService.save(seckillOrder);
//
//        //秒杀成功存入redis中
//        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), seckillOrder);
//
//        return order;
//    }

    /**
     * 秒杀 解决超卖问题
     *
     * @param user
     * @param goods
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order seckill(User user, GoodsVo goods) {
        //秒杀商品表减库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        boolean result = seckillGoodsService.update(seckillGoods,new UpdateWrapper<SeckillGoods>().setSql("stock_count=" + "stock_count-1").eq("goods_id", seckillGoods.getId()).gt("stock_count", 0));
        if (!result  || seckillGoods.getStockCount()<1) {
            //减库存失败，商品卖完了
//            setGoodsOver(goods.getId());
            return null;
        }

        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
//        orderService.save(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);

        //秒杀成功存入redis中
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), seckillOrder);

        return order;
    }



    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null) {
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoById(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);
        return detail;
    }
}
