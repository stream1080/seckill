package com.example.demo.service.Impl;

import com.example.demo.dao.ItemDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Item;
import com.example.demo.entity.Product;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service  //标识这个bean是service层的，并交给spring容器管理
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ProductDao productDao;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String PRODUCT_BYID = "product_byid";

    @Override
    public List<Item> getItemList() {
        return itemDao.queryItem();
    }

    @Override
    public Item getItemById(int itemId) {
        return itemDao.queryItemById(itemId);
    }

    @Transactional
    @Override
    public boolean addItem(Item item) {
        Product product = productDao.queryProductById(item.getProductId());
        if (item.getItemId() == null && !"".equals(item.getItemId()) && product.getStock()>0) {
            try {
                product.setStock(product.getStock()-product.getStock());
                int effec = productDao.updateProduct(product);
                int effectedNum = itemDao.insertItem(item);
                if (effectedNum > 0 && effec > 0) {
                    return true;
                } else {
                    throw new RuntimeException("添加订单失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("添加订单失败" + e.getMessage());
            }
        } else {
            throw new RuntimeException(("订单信息不能为空"));
        }

    }


    @Override
    public boolean modifyItem(Item item) {
        if (item.getItemId() != null && (item.getItemId()) > 0) {
            try {
                int effectedNum = itemDao.updateItem(item);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新订单信息失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新订单信息失败" + e.getMessage());
            }
        } else {
            throw new RuntimeException(("订单信息不能为空"));
        }
    }

    @Override
    public boolean deleteItem(int itemId) {
        if (itemId > 0) {
            try {
                int effectedNum = itemDao.deleteItem(itemId);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("删除订单信息失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("删除订单信息失败" + e.getMessage());
            }
        } else {
            throw new RuntimeException(("订单Id不能为空"));
        }
    }

    @Transactional
    @Override
    public boolean addItemKill(Item item) {
        Map<Integer,Product> productMap = redisTemplate.opsForHash().entries("PRODUCT_BYID");
        Product product = productMap.get(item.getProductId());
        if (item.getItemId() == null && !"".equals(item.getItemId()) && product.getStock()>0) {
            try {
                product.setStock(product.getStock()-item.getAmount());
                productMap.put(product.getProductId(),product);
                redisTemplate.opsForHash().putAll("PRODUCT_BYID",productMap);
                redisTemplate.expire("PRODUCT_BYID",10000, TimeUnit.MILLISECONDS);
                //int effec = productDao.updateProduct(product);
                int effectedNum = itemDao.insertItem(item);
                if (effectedNum > 0 ) {
                    return true;
                } else {
                    throw new RuntimeException("添加订单失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("添加订单失败" + e.getMessage());
            }
        } else {
            throw new RuntimeException(("订单信息不能为空"));
        }

    }




}
