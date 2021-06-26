package com.example.demo.vo;

import com.example.demo.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Title: OrderDetailVo
 * Description:订单详情返回对象
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {

    private Order order;

    private GoodsVo goodsVo;

}
