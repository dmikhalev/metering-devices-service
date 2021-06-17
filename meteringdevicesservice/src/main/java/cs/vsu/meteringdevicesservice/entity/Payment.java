package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum", nullable = false)
    private Double sum;

    @CreatedDate
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "card_id")
    private BankCard bankCard;

    @OneToOne(mappedBy = "payment")
    private Receipt receipt;

    public Payment() {
    }

    public Payment(Double sum) {
        this.sum = sum;
        this.date = new Date();
    }

    public Payment(Long id, Double sum, Date date, BankCard bankCard) {
        this.id = id;
        this.sum = sum;
        this.date = date;
        this.bankCard = bankCard;
    }
}
