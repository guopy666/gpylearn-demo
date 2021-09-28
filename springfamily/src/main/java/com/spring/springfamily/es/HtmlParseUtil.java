package com.spring.springfamily.es;

import com.spring.springfamily.bean.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HtmlParseUtil
 * @Description
 * @Author guopy
 * @Date 2021/9/28 10:05
 */
public class HtmlParseUtil {

    public static void main(String[] args) throws Exception {
        new HtmlParseUtil().params("码出高效").forEach(System.out::println);
    }

    public List<Content> params(String keywords) throws Exception {
        // 获取请求 https://search.jd.com/Search?keyword=java
        // 需要联网
        String url = "https://search.jd.com/Search?keyword=" + keywords + "&enc=utf-8";
        // 解析网页
        Document document = Jsoup.parse(new URL(url), 30000);

        // js的所有方法，在这里都可以用
        Element element = document.getElementById("J_goodsList");
        // 获取所有的li元素
        Elements elements = element.getElementsByTag("li");
        List<Content> contents = new ArrayList<>();

        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            Content content = new Content();
            content.setImg(img);
            content.setTitle(title);
            content.setPrice(price);
            contents.add(content);
        }

        return contents;
    }

}
