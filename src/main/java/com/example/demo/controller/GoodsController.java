package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.GoodsService;
import com.example.demo.service.UserService;
import com.example.demo.vo.GoodsVo;
import lombok.Data;
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
import java.util.Date;

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
        GoodsVo goodsVo = goodsService.findGoodsVoById(goodId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();

        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀结束倒计时
        int endSeconds = (int) ((endDate.getTime() - nowDate.getTime()) / 1000);
        //秒杀还未开始
        if (nowDate.before(startDate)) {
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
            //秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
            endSeconds = 0;
        } else {
            //秒杀进行中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("endSeconds", endSeconds);
        model.addAttribute("goods",goodsVo);
        return "goodsDetail";
    }

}

