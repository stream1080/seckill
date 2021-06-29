package com.example.demo.interceptor;


import com.example.demo.entity.User;

/**
 * Title: UserContext
 * Description:自定义用户参数
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

    
}
