package com.example.demo.controller;


import com.example.demo.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/toList1")
    public String toList1(HttpSession session, Model model, @CookieValue("userTicket") String ticket){
        if(StringUtils.isEmpty(ticket)){
            return "login";
        }
        User user = (User) session.getAttribute(ticket);
        if (null == user){
            return "login";
        }
        model.addAttribute("user",user);
        return "goodsList";
    }

}

