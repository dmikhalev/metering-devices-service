package cs.vsu.meteringdevicesservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prev_amount")
    private Long prevAmount;

    @Column(name = "curr_amount")
    private Long currAmount;

    @Column(name = "personal_code", nullable = false)
    private Long personalCode;

    @CreatedDate
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "receipt_data_id", nullable = false)
    private ReceiptData receiptData;

    @OneToOne()
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;
}
