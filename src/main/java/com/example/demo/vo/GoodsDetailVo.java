package com.example.demo.vo;

import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Title: GoodsDetailVo
 * Description:商品详情返回对象
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailVo {

    private User user;

    private GoodsVo goodsVo;

    private Integer secKillStatus;

    private Long remainSeconds;

    private Long endSeconds;

}
