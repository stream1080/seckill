package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.GoodsService;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 秒杀-商品 前端控制器
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/toList")
    public String toList(Model model, User user){
//        if(StringUtils.isEmpty(ticket)){
//            return "login";
//        }
//        User user = userService.getUserByCookie(ticket,request,response);
////        User user = (User) session.getAttribute(ticket);
//        if (null == user){
//            return "login";
//        }
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        return "goodsList";
    }

    @RequestMapping("/detail/{goodId}")
    public String toDetail(Model model, User user, @PathVariable long goodId){
        model.addAttribute("user",user);
        model.addAttribute("goods",goodsService.findGoodsVoById(goodId));
        return "goodsDetail";
    }

}

