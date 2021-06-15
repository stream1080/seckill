package com.example.demo.dao;

import com.example.demo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper       //标识这个类是一个数据访问层的bean，由spring容器管理
@Repository   //将这个mapper的bean注册到spring容器，不加也行
public interface ProductDao {

    /**
     * 查询所有商品
     * @return
     */
    List<Product> queryProduct();

    /**
     * 根据商品Id查询商品信息
     * @param productId
     * @return
     */
    Product queryProductById(int productId);

    /**
     * 增加商品
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 修改商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除商品
     * @param productId
     * @return
     */
    int deleteProduct(int productId);

}
