import pojo.News;
import util.NewsUtil;

import java.util.List;

public class Text {
    public static void main(String[] args) {
        List<News> news = NewsUtil.getNews("anti.xlsx");
        System.out.println(news);
    }
}
