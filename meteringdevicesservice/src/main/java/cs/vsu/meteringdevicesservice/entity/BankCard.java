package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;

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

    @Column(name = "end_month", nullable = false)
    private Integer endMonth;

    @Column(name = "end_year", nullable = false)
    private Integer endYear;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BankCard() {
    }

    public BankCard(Long id, String name, Long cardNumber, Integer endMonth, Integer endYear) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }
}
