package com.example.demo.entity;

import lombok.Data;

@Data
public class Product {
    //商品Id，唯一主键
    private Integer productId;

    //商品名称
    private String productTitle;

    //商品价格
    private Integer price;

    //商品描述
    private String description;

    //商品图片URL
    private String imgUrl;

    //商品库存
    private Integer stock;

}
