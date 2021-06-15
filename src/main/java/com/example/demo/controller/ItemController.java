package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "订单管理API") // 类文档显示内容
@RequestMapping("/admin")   //地址映射的注解
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 获取订单列表
     * @return
     */
    @ApiOperation(value = "获取订单列表信息") // 接口文档显示内容
    @RequestMapping(value = "listitem",method = RequestMethod.GET)
    private Map<String,Object> listItem(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<Item> list = itemService.getItemList();
        modelMap.put("itemList",list);
        return modelMap;
    }

    /**
     * 通过订单Id获取订单信息
     *
     * @return
     */
    @ApiOperation(value = "通过订单Id获取订单信息") // 接口文档显示内容
    @RequestMapping(value = "/getitembyid", method = RequestMethod.GET)
    private Map<String, Object> getItemById(Integer itemId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取订单信息
        Item item = itemService.getItemById(itemId);
        modelMap.put("item", item);
        return modelMap;
    }


    /**
     * 添加订单信息
     *
     * @param itemStr
     * @param request
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @ApiOperation(value = "添加订单信息") // 接口文档显示内容
    @RequestMapping(value = "/additem", method = RequestMethod.POST)
    private Map<String,Object> addItem(@RequestBody Item item)
            throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", itemService.addItem(item));
        return modelMap;
    }


    /**
     * 修改订单信息
     *
     * @param itemStr
     * @param request
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @ApiOperation(value = "修改订单信息") // 接口文档显示内容
    @RequestMapping(value = "/modifyitem", method = RequestMethod.POST)
    private Map<String, Object> modifyItem(@RequestBody Item item)
            throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", itemService.modifyItem(item));
        return modelMap;
    }


    /**
     * 删除订单信息
     * @param itemId
     * @return
     */
    @ApiOperation(value = "删除订单信息") // 接口文档显示内容
    @RequestMapping(value = "/removeitem", method = RequestMethod.GET)
    private Map<String, Object> removeItem(Integer itemId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", itemService.deleteItem(itemId));
        return modelMap;
    }


    @RequestMapping(value = "/additemkill", method = RequestMethod.POST)
    private Map<String,Object> addItemKill(@RequestBody Item item)
            throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", itemService.addItemKill(item));
        return modelMap;
    }
}
