package com.example.demo.vo;

import com.example.demo.validator.IsMobile;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Title: LoginVO
 * Description:登录参数接收对象
 *
 */
@Data
public class LoginVO {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 6,max = 32)
    private String password;
}
