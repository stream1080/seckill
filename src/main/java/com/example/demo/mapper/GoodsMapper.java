package com.example.demo.mapper;

import com.example.demo.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 秒杀-商品 Mapper 接口
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询商品列表
     * @return
     */
    @Select("SELECT g.id,\n" +
            "               g.goods_name,\n" +
            "               g.goods_title,\n" +
            "               g.goods_img,\n" +
            "               g.goods_detail,\n" +
            "               g.goods_price,\n" +
            "               g.goods_stock,\n" +
            "               sg.seckill_price,\n" +
            "               sg.stock_count,\n" +
            "               sg.start_date,\n" +
            "               sg.end_date\n" +
            "        FROM t_goods g\n" +
            "                 LEFT JOIN t_seckill_goods AS sg ON g.id = sg.goods_id")
    List<GoodsVo> findGoodsVo();

    /**
     * 查询商品详情
     * @param goodId
     * @return
     */
    @Select("SELECT g.id,\n" +
            "               g.goods_name,\n" +
            "               g.goods_title,\n" +
            "               g.goods_img,\n" +
            "               g.goods_detail,\n" +
            "               g.goods_price,\n" +
            "               g.goods_stock,\n" +
            "               sg.seckill_price,\n" +
            "               sg.stock_count,\n" +
            "               sg.start_date,\n" +
            "               sg.end_date\n" +
            "        FROM t_goods g\n" +
            "                 LEFT JOIN t_seckill_goods AS sg ON g.id = sg.goods_id\n" +
            "\t\t\t\t\t\t\t\t WHERE g.id = #{goodId}")
    GoodsVo findGoodsVoById(long goodId);
}
