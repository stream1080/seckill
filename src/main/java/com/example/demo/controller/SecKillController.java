package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Order;
import com.example.demo.entity.SeckillOrder;
import com.example.demo.entity.User;
import com.example.demo.exception.GlobalException;
import com.example.demo.interceptor.AccessLimit;
import com.example.demo.rabbitmq.SecKillRabbitmqSender;
import com.example.demo.rabbitmq.SeckillMessage;
import com.example.demo.service.GoodsService;
import com.example.demo.service.OrderService;
import com.example.demo.service.SeckillGoodsService;
import com.example.demo.service.SeckillOrderService;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import com.google.gson.Gson;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 秒杀-前端控制器
 * </p>
 *
 * @author stream
 * @since 2021-06-24
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SecKillRabbitmqSender secKillRabbitmqSender;

    @Autowired
    private RedisScript<Long> redisScript;

    private Gson gson = new Gson();

    //内存标记，标记商品是否还有库存
    private Map<Long,Boolean> emptyStockMap = new HashMap<>();


    /**
     * redis预减库存
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSecKill(User user, Long goodsId) {
        //判断用户是否登录
        if (user == null) {
            log.info("user={}",user);
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();

        //判断是否重复抢购
        SeckillOrder seckillOrder =
                (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        //内存标记，减少Redisd访问
        if (emptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        //预减库存-redis原子操作
        //Long stock = valueOperations.decrement("seckillGoods:"+goodsId);

        //预减库存-redis分布式锁+lua脚本
        Long stock = (Long) redisTemplate.execute(redisScript,Collections.singletonList("seckillGoods:" + goodsId),
                Collections.EMPTY_LIST);

        if (stock<0){
            emptyStockMap.put(goodsId,true);
            valueOperations.increment("seckillGoods:"+goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        SeckillMessage seckillMessage = new SeckillMessage(user,goodsId);
        secKillRabbitmqSender.sendSeckillMessage(seckillMessage);
        return RespBean.success(0);
    }


    /**
     * 秒杀 静态化   解决超卖问题    redis预减库存 rabbitmq消息入队    隐藏秒杀接口地址
     * 10000*1*10
     * windows优化前QPS:467.5/sec
     * linux优化前QPS:94.4/sec
     * <p>
     * windows优化后QPS:1315.4/sec
     * linux优化后QPS:94.4/sec
     *
     * @param user
     * @param goodsId
     * @return
     */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/{path}/doSeckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSecKillPath(User user, @RequestParam("goodsId") Long goodsId, @PathVariable("path") String path) {
        //判断用户是否登录
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }

        //隐藏接口，验证path
        boolean check = orderService.checkPath(user, goodsId, path);
        if (!check) {
            return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
        }

        //内存标记，减少redis访问
        boolean over = emptyStockMap.get(goodsId);
        if (over) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        //redis操作
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断是否重复抢购,从redis中读取
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        //redis预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock < 0) {
            emptyStockMap.put(goodsId, true);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        //入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(goodsId);
        secKillRabbitmqSender.sendSeckillMessage(seckillMessage);

        return RespBean.success();
    }



    /**
     * 获取秒杀地址
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public RespBean getPath(User user,Long goodsId,String verifyCode) {
        //判断用户是否登录
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        //检查验证码
        boolean check = orderService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check) {
            return RespBean.error(RespBeanEnum.VERIFYCODE_ERROR);
        }
        String str = orderService.createPath(user,goodsId);
        return RespBean.success(str);
    }



    /**
     * 获取秒杀结果：
     * orderId：成功
     *      -1：秒杀失败
     *       0：排队中
     *
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public RespBean secKillResult(User user, Long goodsId) {
        //判断用户是否登录
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        long result = seckillOrderService.getSeckillResult(user.getId(), goodsId);
        return RespBean.success(result);
    }


    @RequestMapping(value = "/doSeckill3", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSecKill3(User user, Long goodsId) {
        //判断用户是否登录
        if (user == null) {
            log.info("user={}",user);
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        GoodsVo goods = goodsService.findGoodsVoById(goodsId);

        //判断库存是否足够
        if (goods.getStockCount() < 1) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        Order order = orderService.seckill(user, goods);
        return RespBean.success(order);
    }


    @RequestMapping("/doSeckill2")
    public String doSecKill2(Model model, User user, Long goodsId) {
        //判断用户是否登录
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoById(goodsId);

        //判断库存是否足够
        if (goods.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }

        //判断是否重复抢购
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "secKillFail";
        }
        Order order = orderService.seckill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        return "orderDetail";
    }


    /**
     * 获取验证码
     *
     * @param response
     * @param user
     * @param goodsId
     * @return
     */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public void getVerifyCode(HttpServletResponse response, User user, Long goodsId) {
        //判断用户是否登录
        if (user == null) {
//            return RespBean.error(RespBeanEnum.SESSION_ERROR);
            throw new GlobalException(RespBeanEnum.SESSION_ERROR);
        }
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成验证码，将结果放入Redis
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130,32,3);
        redisTemplate.opsForValue().set("captcha"+user.getId()+":"+goodsId,
                captcha.text(),300, TimeUnit.SECONDS);
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.error("验证码生成失败"+e.getMessage());
        }
//        return RespBean.success();
    }

    /**
     * 系统初始化后，将商品库存数量加载到redis缓存
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(GoodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:"+GoodsVo.getId(),
                    GoodsVo.getStockCount());
            emptyStockMap.put(GoodsVo.getId(), false);
        });
    }
}

