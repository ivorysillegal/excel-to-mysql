package pojo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class News {

    private Integer news_id;

    private String pic_url;

    private String title;

    private String main;

    private LocalDate upload_time;

    public News(String pic_url, String title, String main, LocalDate upload_time) {
        this.pic_url = pic_url;
        this.title = title;
        this.main = main;
        this.upload_time = upload_time;
    }

    public News() {
    }
}

