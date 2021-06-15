package com.example.demo.dao;

import com.example.demo.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper       //标识这个类是一个数据访问层的bean，由spring容器管理
@Repository   //将这个mapper的bean注册到spring容器，不加也行
public interface ItemDao {

    /**
     * 查询所有订单
     * @return
     */
    List<Item> queryItem();

    /**
     * 根据订单Id查询订单信息
     * @param itemId
     * @return
     */
    Item queryItemById(int itemId);

    /**
     * 增加订单
     * @param item
     * @return
     */
    int insertItem(Item item);

    /**
     * 修改订单信息
     * @param item
     * @return
     */
    int updateItem(Item item);

    /**
     * 删除订单
     * @param itemId
     * @return
     */
    int deleteItem(int itemId);

}
