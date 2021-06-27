package com.example.demo;

import com.example.demo.entity.Goods;
import com.example.demo.mapper.GoodsMapper;
import com.example.demo.rabbitmq.MqSender;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private MqSender mqSender;

    @Test
    void mqTest(){
        mqSender.send("Hello");
    }

    @Test
    void mqTestFanout(){
//        mqSender.sendDirectRed("Hello-Red");
        mqSender.sendDirectGreen("Hello-greed");
    }



    @Test
    void contextLoads() throws Exception {
        RequestGoodsInfo RequestGoodsInfo = new RequestGoodsInfo();
        List<Goods> goodsList = new RequestGoodsInfo.HtmlParseUtil().parseJD("java");
        for (Goods goods:goodsList) {
            goodsMapper.insert(goods);
        }
    }

}
//    @Resource
//    private RedisTemplate redisTemplate;
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Autowired
//    private ProductService productService;
//    private static final String KILL_PRODUCT_LIST = "kill_product_list";
//
//    @Test
//    public void testRedis(){
//        //增加key，value
//        redisTemplate.opsForValue().set("name","springboot");
//        //查询
//        String name = (String) redisTemplate.opsForValue().get("name");
//        System.out.println(name);
//
//        //删除
//        redisTemplate.delete("name");
//
//        //更新
//        redisTemplate.opsForValue().set("name","更新");
//        name = (String) redisTemplate.opsForValue().get("name");
//        System.out.println(name);
//    }
//
//    @Test
//    public void testRedis_Hash(){
//        String KILL_PRODUCT_LIST = "kill_product_list";
//            Map<String, String> productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
//            if(productMap.isEmpty()){
//         //       productList = getProductList();
//                productMap.put(KILL_PRODUCT_LIST,"秒杀");
//                //  redisTemplate.delete(KILL_PRODUCT_LIST);
//                redisTemplate.opsForHash().putAll(KILL_PRODUCT_LIST,productMap);
//                // redisTemplate.opsForList().leftPushAll(KILL_PRODUCT_LIST,productList);
//                redisTemplate.expire(KILL_PRODUCT_LIST,100000, TimeUnit.MILLISECONDS);
//
//            }
//        productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
//        System.out.println(redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST).values());
//            //productMap = redisTemplate.opsForHash(KILL_PRODUCT_LIST);
////            productList = redisTemplate.opsForList().range(KILL_PRODUCT_LIST,0,-1);
////            productList = productMap.get(KILL_PRODUCT_LIST);
////            return productList;
//
//    }
//
//    @Test
//    public void testProductList(){
//        Map<String,String> productMap = redisTemplate.opsForHash().entries(KILL_PRODUCT_LIST);
//        List<Product> productList = null;
//        productList = productDao.queryProduct();
//        //productMap.put("list",productList);
//        productMap.put("list","productList");
//        redisTemplate.delete(KILL_PRODUCT_LIST);
//        redisTemplate.opsForHash().putAll(KILL_PRODUCT_LIST,productMap);
//        redisTemplate.expire(KILL_PRODUCT_LIST,100000, TimeUnit.MILLISECONDS);
//        System.out.println(productList.get(0));
//        assertEquals("productList", productList.get(0));
//    }
//
//    @Test
//    public void testProductListCache(){
//        List<Product> productList = productService.getProductListCache();
//        for (Product product: productList) {
//            System.out.println(product.getProductTitle());
//        }
//    }
//
//    @Test
//    public void testProductByIdCache(){
//        for (int i = 0; i < 5; i++) {
//            Product product = productService.getProductByIdCache(701);
//            System.out.println(product.getProductTitle());
//        }
//    }




