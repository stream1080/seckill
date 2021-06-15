package com.example.demo.service.Impl;

import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service  //标识这个bean是service层的，并交给spring容器管理
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Resource
    private RedisTemplate redisTemplate;
    private static final String KILL_PRODUCT_LIST = "kill_product_list";

    @Override
    public List<Product> getProductList() {
        return productDao.queryProduct();
    }

    @Override
    public Product getProductById(int productId) {
        return productDao.queryProductById(productId);
    }

    @Transactional
    @Override
    public boolean addProduct(Product product) {
        if (product.getProductId() == null && !"".equals(product.getProductId())) {
            try {
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("添加商品失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("添加商品失败" + e.getMessage());
            }
        } else {
            throw new RuntimeException(("商品信息不能为空"));
        }

    }


    @Override
    public boolean modifyProduct(Product product) {
        if (product.getProductId() != null && (product.getProductId()) > 0) {
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新商品信息失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新商品信息失败" + e.getMessage());
            }
        } else {
            throw new RuntimeException(("商品信息不能为空"));
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        if (productId > 0) {
            try {
                int effectedNum = productDao.deleteProduct(productId);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除商品信息失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除商品信息失败" + e.getMessage());
            }
        } else {
            throw new RuntimeException(("商品Id不能为空"));
        }
    }


    @Override
    public List<Product> getProductListCache() {
        try{
            Map<String,List> productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
            List<Product> productList = null;
            if(productMap.isEmpty()){
                productList = getProductList();
                productMap.put(KILL_PRODUCT_LIST,productList);
                redisTemplate.opsForHash().putAll(KILL_PRODUCT_LIST,productMap);
                redisTemplate.expire(KILL_PRODUCT_LIST,100000, TimeUnit.MILLISECONDS);
                return productList;
            }
            productList = productMap.get(KILL_PRODUCT_LIST);
            return productList;
        }catch (Exception e){
            System.out.println("查询异常"+ e.getMessage());
            return getProductList();
        }
    }


    @Override
    public Product getProductByIdCache(int productId) {
        try{
            Map<Integer,Product> productMap = redisTemplate.opsForHash().entries("PRODUCT_BYID");
            Product product = null;
            if(productMap.isEmpty()){
                productMap.put(productId,getProductById(productId));
                redisTemplate.opsForHash().putAll("PRODUCT_BYID",productMap);
                redisTemplate.expire("PRODUCT_BYID",10000, TimeUnit.MILLISECONDS);
                System.out.println("缓存");
                return getProductById(productId);
            }
            product = productMap.get(productId);
            return product;
        }catch (Exception e){
            System.out.println("查询异常"+ e.getMessage());
            return getProductById(productId);
        }

    }


}
//
//
//    @Override
//    public List<Product> findAllCache() {
//        try{
//            Map<String,Product> productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
//            List<Product> productList = null;
//            if(productMap.isEmpty()){
//                productList = productDao.queryProduct();
//                redisTemplate.opsForHash().putAll(KILL_PRODUCT_LIST,productMap);
//                redisTemplate.expire(KILL_PRODUCT_LIST,10000, TimeUnit.MILLISECONDS);
//                return productList;
//            }
//            // productList = productList.add(productMap);
//            return productList;
//        }catch (Exception e){
//            //logger.error("findAllcache error",e);
//            return getProductList();
//        }
//    }
