package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Item {
    //订单Id
    private Integer itemId;
    //用户Id
    private Integer userId;
    //商品Id
    private Integer productId;
    //商品数量
    private Integer amount;
    //订单总价
    private Integer total;
    //创建时间
    private Date createTime;
    //订单状态
    private String status;

}