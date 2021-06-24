package com.example.demo.utils;

import java.util.UUID;

/**
 * Title: UUIDUtil
 * Description:
 *
 */
public class UUIDUtil {

    /**
     * 去掉UUID的“-”
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
