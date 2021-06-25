package com.example.demo.controller;


import com.example.demo.service.UserService;
import com.example.demo.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>
 * 秒杀-秒杀用户 前端控制器
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginUserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转登录页面
     *
     * @return 登录页面
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录功能
     *
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
//    @RequestMapping("/doLogin")
//    @ResponseBody
//    public Object doLogin(@Valid LoginVO loginVo, HttpServletRequest request, HttpServletResponse response) {
//        return userService.doLogin(loginVo, request, response);
//    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Object doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        log.info("login={}"+loginVo);
        return userService.doLogin(loginVo,request,response);
    }

}

