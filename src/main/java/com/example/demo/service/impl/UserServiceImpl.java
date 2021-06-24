package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.exception.GlobalException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.MD5Util;
import com.example.demo.utils.ValidatorUtil;
import com.example.demo.vo.LoginVO;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀-秒杀用户 服务实现类
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;

    /**
     * 登录功能
     * @param loginVO
     * @return
     */
    @Override
    public RespBean doLogin(LoginVO loginVO) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
//        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //根据手机号码获取用户
        User user = userMapper.selectById(mobile);
        if (null == user) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if (!MD5Util.fromPassToDbPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
//        //生成Cookie
//        String ticket = UUIDUtil.uuid();
//        request.getSession().setAttribute(ticket, user);
//        //将用户信息存入redis中
//        CookieUtil.setCookie(request, response, "userTicket", ticket);
//        return RespBean.success(ticket);
        return RespBean.success();
    }
}
