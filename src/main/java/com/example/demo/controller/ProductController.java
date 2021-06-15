//package com.example.demo.controller;
//
//import com.example.demo.entity.Product;
//import com.example.demo.service.ProductService;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@Api(tags = "商品管理API") // 类文档显示内容
//@RequestMapping("/admin")   //地址映射的注解
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    /**
//     * 获取商品列表
//     * @return
//     */
//    @ApiOperation(value = "获取商品列表信息") // 接口文档显示内容
//    @RequestMapping(value = "listproduct",method = RequestMethod.GET)
//    private Map<String,Object> listProduct(){
//        Map<String,Object> modelMap = new HashMap<String,Object>();
//        List<Product> list = productService.getProductList();
//        modelMap.put("productList",list);
//        return modelMap;
//    }
//
//    /**
//     * 通过商品Id获取商品信息
//     *
//     * @return
//     */
//    @ApiOperation(value = "通过商品Id获取商品信息") // 接口文档显示内容
//    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
//    private Map<String, Object> getProductById(Integer productId) {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        // 获取商品信息
//        Product product = productService.getProductById(productId);
//        modelMap.put("product", product);
//        return modelMap;
//    }
//
//
//    /**
//     * 添加商品信息
//     *
//     * @param productStr
//     * @param request
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    @ApiOperation(value = "添加商品信息") // 接口文档显示内容
//    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
//    private Map<String,Object> addProduct(@RequestBody Product product)
//            throws JsonParseException, JsonMappingException, IOException {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        modelMap.put("success", productService.addProduct(product));
//        return modelMap;
//    }
//
//
//    /**
//     * 修改商品信息
//     *
//     * @param productStr
//     * @param request
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    @ApiOperation(value = "修改商品信息") // 接口文档显示内容
//    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
//    private Map<String, Object> modifyProduct(@RequestBody Product product)
//            throws JsonParseException, JsonMappingException, IOException {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        modelMap.put("success", productService.modifyProduct(product));
//        return modelMap;
//    }
//
//
//    /**
//     * 删除商品信息
//     * @param productId
//     * @return
//     */
//    @ApiOperation(value = "删除商品信息") // 接口文档显示内容
//    @RequestMapping(value = "/removeproduct", method = RequestMethod.GET)
//    private Map<String, Object> removeProduct(Integer productId) {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        modelMap.put("success", productService.deleteProduct(productId));
//        return modelMap;
//    }
//
//
//    /**
//     * 获取缓存商品列表
//     * @return
//     */
//    @RequestMapping(value = "listproductcache",method = RequestMethod.GET)
//    private Map<String,Object> cacheProduct(){
//        Map<String,Object> modelMap = new HashMap<String,Object>();
//        List<Product> products = productService.getProductListCache();
//        modelMap.put("productList",products);
//        return modelMap;
//    }
//
//    @RequestMapping(value = "getproductbyidcache",method = RequestMethod.GET)
//    private Map<String, Object> getProductByIdCache(Integer productId) {
//        Map<String, Object> modelMap = new HashMap<String, Object>();
//        // 获取商品信息
//        Product product = productService.getProductByIdCache(productId);
//        modelMap.put("product", product);
//        return modelMap;
//    }
//}
