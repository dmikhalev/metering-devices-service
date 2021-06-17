package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cs.vsu.meteringdevicesservice.entity.News;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDto {
    private Long id;
    private String text;
    private Date date;

    public NewsDto() {

    }

    public NewsDto(Long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public News toNews() {
        if (date == null) {
            date = new Date();
        }
        return new News(id, text, date);
    }

    public static NewsDto fromNews(News news) {
        return new NewsDto(news.getId(), news.getText(), news.getDate());
    }
}
