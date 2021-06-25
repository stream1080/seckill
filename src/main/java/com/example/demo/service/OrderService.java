package com.example.demo.service;

import com.example.demo.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import com.example.demo.vo.GoodsVo;

/**
 * <p>
 * 秒杀-订单 服务类
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
public interface OrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    Order seckill(User user, GoodsVo goods);
}
