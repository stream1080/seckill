package com.example.demo.utils;


import org.springframework.util.DigestUtils;

public class MD5Util {

    public static final String salt = "1a2b3c4d";

    /**
     * 获取md5
     * @param src
     * @return
     */
    public static String md5(String src){
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }

    public static String inputPasstoFromPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String fromPassToDbPass(String inputPass,String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass,String salt){
        String fromPass = inputPasstoFromPass(inputPass);
        String dbPass = fromPassToDbPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPasstoFromPass("123456"));
        System.out.println(fromPassToDbPass("d3b1294a61a07da9b49b6e22b2cbd7f9","1a2b3c4d"));
        System.out.println(inputPassToDbPass("123456","1a2b3c4d"));
    }
}
