package com.example.demo.interceptor;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.CookieUtil;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Title: AccessInterceptor
 * Description:
 *
 */
@Service
public class AccessInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            //System.out.println(methodName);
            if ("toLogin".equals(methodName) || "doLogin".equals(methodName)) {
                return true;
            }
            //获取cookie中userTicket
            String ticket = CookieUtil.getCookieValue(request, "userTicket");
            if (StringUtils.isEmpty(ticket)) {
                return false;
            }
            //根据cookie中获取的userTicket从redis中获取用户信息
            User user = userService.getUserByCookie(ticket, request, response);
            // 将用户信息存入ThreadLocal
            UserContext.setUser(user);


            //获取注解信息
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(response,RespBeanEnum.SESSION_ERROR);
                    return false;
                }
                key = key + ":" + user.getId();
            } else {
                //TODO 未登录-do nothing
            }
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            if (count == null) {
                redisTemplate.opsForValue().set(key, 1, seconds, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                redisTemplate.opsForValue().increment(key);
            } else {
                render(response,RespBeanEnum.ACCESS_LIMIT_REACHEED);
                return false;
            }
        }
        return true;
    }

    /**
     * 构造返回对象
     *
     * @param response
     * @param respBeanEnum
     * @throws Exception
     */
    private void render(HttpServletResponse response, RespBeanEnum respBeanEnum) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RespBean respBean = RespBean.error(respBeanEnum);
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }

}
