package com.example.demo;

import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductDao productDao;
    private static final String KILL_PRODUCT_LIST = "kill_product_list";

    @Test
    public void testRedis(){
        //增加key，value
        redisTemplate.opsForValue().set("name","springboot");
        //查询
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);

        //删除
        redisTemplate.delete("name");

        //更新
        redisTemplate.opsForValue().set("name","更新");
        name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    public void testRedis_Hash(){
        String KILL_PRODUCT_LIST = "kill_product_list";
            Map<String, String> productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
            if(productMap.isEmpty()){
         //       productList = getProductList();
                productMap.put(KILL_PRODUCT_LIST,"秒杀");
                //  redisTemplate.delete(KILL_PRODUCT_LIST);
                redisTemplate.opsForHash().putAll(KILL_PRODUCT_LIST,productMap);
                // redisTemplate.opsForList().leftPushAll(KILL_PRODUCT_LIST,productList);
                redisTemplate.expire(KILL_PRODUCT_LIST,100000, TimeUnit.MILLISECONDS);

            }
        productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
        System.out.println(redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST).values());
            //productMap = redisTemplate.opsForHash(KILL_PRODUCT_LIST);
//            productList = redisTemplate.opsForList().range(KILL_PRODUCT_LIST,0,-1);
//            productList = productMap.get(KILL_PRODUCT_LIST);
//            return productList;

    }

    @Test
    public void testProductList(){
        Map<String,String> productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
        List<Product> productList = null;
        productList = productDao.queryProduct();
        //productMap.put("list",productList);
        productMap.put("list","productList");
        redisTemplate.delete(KILL_PRODUCT_LIST);
        redisTemplate.opsForHash().putAll(KILL_PRODUCT_LIST,productMap);
        redisTemplate.expire(KILL_PRODUCT_LIST,100000, TimeUnit.MILLISECONDS);
        System.out.println(productList.get(0));
        assertEquals("productList", productList.get(0));
    }





    @Test
    void contextLoads() {
    }

}
