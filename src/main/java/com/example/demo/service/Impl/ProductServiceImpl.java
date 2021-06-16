package com.example.demo.service.Impl;

import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service  //标识这个bean是service层的，并交给spring容器管理
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

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
        String productJson = redisTemplate.opsForValue().get("productJson");
        if (StringUtils.isEmpty(productJson)) {
            List<Product> productList = getProductList();
            String s = gson.toJson(productList);
            redisTemplate.opsForValue().set("productJson", s);
            return productList;
        }
        List<Product> result = gson.fromJson(productJson, new TypeToken<List<Product>>(){}.getType());
        return result;
    }


    @Override
    public Product getProductByIdCache(int productId) {
        String productCache = redisTemplate.opsForValue().get(String.valueOf(productId));
        if (StringUtils.isEmpty(productCache)) {
            Product product = getProductById(productId);
            String s = gson.toJson(product);
            redisTemplate.opsForValue().set(String.valueOf(productId), s);
            return product;
        }
        Product result = gson.fromJson(productCache, Product.class);
        return result;
    }
}

