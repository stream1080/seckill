package com.example.demo.service;

import com.example.demo.entity.Item;

import java.util.List;


public interface ItemService {

    /**
     *
     * 获取订单列表
     * @return
     */
    List<Item> getItemList();

    /**
     * 根据订单Id获取订单信息
     * @param itemId
     * @return
     */
    Item getItemById(int itemId);

    /**
     * 增加订单信息
     * @param item
     * @return
     */
    boolean addItem(Item item);

    /**
     * 修改订单信息
     * @param item
     * @return
     */
    boolean modifyItem(Item item);

    /**
     * 删除订单信息
     * @param itemId
     * @return
     */
    boolean deleteItem(int itemId);

    /**
     * Redis缓存秒杀
     * @param item
     * @return
     */
    boolean addItemKill(Item item);

}
