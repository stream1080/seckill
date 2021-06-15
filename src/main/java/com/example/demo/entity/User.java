package com.example.demo.entity;

import lombok.Data;

@Data
public class User {

    //主键，唯一识别Id
    private Integer userId;

    //姓名
    private String userName;

    //年龄
    private Integer age;

    //性别
    private String sex;

    //电话
    private String telephone;

    //地址
    private String address;

    //密码
    private String password;

    //用户类型
    private String userType;

}
