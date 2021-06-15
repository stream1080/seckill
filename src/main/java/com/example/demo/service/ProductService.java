package com.example.demo.service;

import com.example.demo.entity.Product;

import java.util.Collection;
import java.util.List;


public interface ProductService {

    /**
     *
     * 获取商品列表
     * @return
     */
    List<Product> getProductList();

    /**
     * 根据商品Id获取商品信息
     * @param productId
     * @return
     */
    Product getProductById(int productId);

    /**
     * 增加商品信息
     * @param product
     * @return
     */
    boolean addProduct(Product product);

    /**
     * 修改商品信息
     * @param product
     * @return
     */
    boolean modifyProduct(Product product);

    /**
     * 删除商品信息
     * @param productId
     * @return
     */
    boolean deleteProduct(int productId);

    /**
     * 查询所有缓存的商品
     * @return
     */
    List<Product> getProductListCache();


    /**
     * 查询指定商品的缓存
     * @param productId
     * @return
     */
    Product getProductByIdCache(int productId);

}
