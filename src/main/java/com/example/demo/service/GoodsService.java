package com.example.demo.service;

import com.example.demo.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 秒杀-商品 服务类
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();


    /**
     * 查询商品详情
     * @param goodId
     * @return
     */
    GoodsVo findGoodsVoById(long goodId);

}
