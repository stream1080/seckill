package com.example.demo;

import com.example.demo.entity.Goods;
import com.example.demo.mapper.GoodsMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class RequestGoodsInfo {

    @Autowired
    private GoodsMapper goodsMapper;

    public static void main(String[] args) throws Exception {
        new HtmlParseUtil().parseJD("java").forEach(System.out::println);
//        List<Goods> goodsList = new HtmlParseUtil().parseJD("iphone");
    }

    /**
     * description: 网页解析工具类
     */
    @Component
    public static class HtmlParseUtil {
        /**
         * 解析jd搜索网页
         *
         * @param keywords
         * @return
         * @throws Exception
         */
        public List<Goods> parseJD(String keywords) throws Exception {


            //URL会对符号和汉字转码要先转码再拼接, 否则URL无法解析 (因为会将url中的符号也一起转码, 无法识别)
            String urlKeywords = URLEncoder.encode(keywords, "UTF-8");

            //获取请求  https://search.jd.com/Search?keyword=java 前提，需要联网
            String url = "https://search.jd.com/Search?keyword=" + urlKeywords + "&enc=utf-8";

            //解析网页。(Jsoup返回Document就是浏览器Document对象)
            Document document = Jsoup.parse(new URL(url), 30000);

            //所有你在js中可以使用的方法，这里都能用！
            Element element = document.getElementById("J_goodsList");

            // 获取所有的li元素
            Elements li = element.getElementsByTag("li");

            //获取元素中的内容,这里的el就是 每一个li标签了
            ArrayList<Goods> goodsList = new ArrayList<>();

            for (Element el : li) {
                String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
                String price = el.getElementsByClass("p-price").eq(0).text();
                String title = el.getElementsByClass("p-name").eq(0).text();

//                System.out.println(price.substring(1,price.length()-1));

                Goods goods =new Goods();
                goods.setGoodsName(title.length()==0?"null":title.substring(0,title.length()>10?10:title.length()));
                goods.setGoodsTitle(title.length()==0?"null":title.substring(0,title.length()>20?20:title.length()));
                goods.setGoodsImg(img);
//                price = price.length()==0?"0.00":title.substring(1);
                BigDecimal bd = new BigDecimal(998);
////                System.out.println(bd);
                goods.setGoodsPrice(bd);
                goods.setGoodsDetail(title);

                goodsList.add(goods);

            }

            return goodsList;
        }
    }
}
