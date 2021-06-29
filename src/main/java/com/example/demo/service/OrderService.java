package com.example.demo.service;

import com.example.demo.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.OrderDetailVo;

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

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);

    /**
     * 获取秒杀地址
     *
     * @param user
     * @param goodsId
     * @return
     */
    String createPath(User user, Long goodsId);

    /**
     * 校验秒杀地址
     *
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    boolean checkPath(User user, Long goodsId, String path);
}
