package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @CreatedDate
    @Column(name = "date", nullable = false)
    private Date date = new Date();

    public News() {

    }

    public News(Long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }
}
