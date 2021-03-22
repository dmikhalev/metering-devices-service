package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum", nullable = false)
    private Long sum;

    @CreatedDate
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne()
    @JoinColumn(name = "card_id", nullable = false)
    private BankCard bankCard;

    @ManyToOne()
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;
}
