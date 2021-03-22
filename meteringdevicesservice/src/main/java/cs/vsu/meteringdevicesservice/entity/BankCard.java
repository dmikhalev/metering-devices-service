package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bank_card")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "card_number", nullable = false)
    private Long cardNumber;

    @CreatedDate
    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Column(name = "cvv", nullable = false)
    private Integer cvv;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
