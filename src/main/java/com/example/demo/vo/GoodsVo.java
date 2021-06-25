package com.example.demo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.entity.Goods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsVo extends Goods {

    /**
     * 秒杀价
     */
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    @TableField("stock_count")
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    @TableField("start_date")
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    @TableField("end_date")
    private Date endDate;
}
