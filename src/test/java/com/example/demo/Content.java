package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 爬虫获取的信息封装的实体类 Content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String title;
    private String price;
    private String img;
}
