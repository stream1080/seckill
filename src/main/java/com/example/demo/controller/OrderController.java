package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import com.example.demo.vo.OrderDetailVo;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀-订单 前端控制器
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单详情
     *
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId){
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail = orderService.detail(orderId);

        return RespBean.success(detail);
    }
}

