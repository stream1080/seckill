package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.vo.LoginVo;
import com.example.demo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 秒杀-秒杀用户 服务类
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
public interface UserService extends IService<User> {

    /**
     * 登录功能
     *
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据cookie获取用户
     *
     * @param userTicket
     * @param request
     * @param response
     * @return
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);


    /**
     * 修改密码
     * @param userTicket
     * @param password
     * @param request
     * @param response
     * @return
     */
    RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response);

}
