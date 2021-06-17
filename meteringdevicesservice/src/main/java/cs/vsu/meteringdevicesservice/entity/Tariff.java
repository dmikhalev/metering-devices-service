package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "tariff")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @CreatedDate
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    public Tariff() {

    }

    public Tariff(Long id, Double cost, Date date, Service service) {
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.service = service;
    }
}
