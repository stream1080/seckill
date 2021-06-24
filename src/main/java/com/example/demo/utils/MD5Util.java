package com.example.demo.utils;


import lombok.val;
import org.springframework.data.redis.core.script.DigestUtils;

public class MD5Util {

    public static String md5(String src){
        return DigestUtils.sha1DigestAsHex(src);
    }

    public static final String salt = "1a2b3c4d";

    public static String inputPasstoFromPass(String inputPass){
        String str = salt.charAt(0)+inputPass+salt.charAt(2)+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String fromPassToDbPass(String inputPass,String salt){
        String str = salt.charAt(0)+inputPass+salt.charAt(2)+salt.charAt(5)+salt.charAt(4);
        return md5(salt);
    }

    public static String inputPassToDbPass(String inputPass,String salt){
        String fromPass = inputPasstoFromPass(inputPass);
        String dbPass = fromPassToDbPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPasstoFromPass("123456"));
        System.out.println(fromPassToDbPass("2070296ce61cc7b4cd130df0da5743ffca0ab403","1a2b3c4d"));
        System.out.println(inputPassToDbPass("123456","1a2b3c4d"));
    }
}
