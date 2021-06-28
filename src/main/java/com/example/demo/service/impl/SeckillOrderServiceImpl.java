package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.SeckillOrder;
import com.example.demo.mapper.SeckillOrderMapper;
import com.example.demo.service.SeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀-秒杀订单 服务实现类
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取秒杀结果
     *
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public long getSeckillResult(Long userId, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", userId).eq("goods_id", goodsId));
        //TODO 数据库未写入订单
        if (seckillOrder != null) {
            //秒杀成功
            return seckillOrder.getOrderId();
        } else if(redisTemplate.hasKey("isStockEmpty:"+goodsId)){
            return -1;
        }else
            return 0;
    }

}
