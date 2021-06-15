package com.example.demo.entity;

import java.io.Serializable;

public class Product implements Serializable {
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

    //商品销量
    private Integer sales;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }
}
