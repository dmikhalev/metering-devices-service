package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cs.vsu.meteringdevicesservice.entity.BankCard;
import cs.vsu.meteringdevicesservice.entity.Payment;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDto {
    private Long id;
    private Double sum;
    private Date date;

    private Long prevAmount;
    private Long currAmount;

    private String serviceName;
    private Long personalCode;

    private Double tariffCost;

    private Long bankCardId;
    private String bankCardName;
    private Long bankCardNumber;
    private Integer bankCardEndMonth;
    private Integer bankCardEndYear;

    private Long receiptId;

    public PaymentDto() {
    }

    public PaymentDto(Long id, Double sum, Date date, Long bankCardId, String bankCardName, Long bankCardNumber, Integer bankCardEndMonth, Integer bankCardEndYear) {
        this.id = id;
        this.sum = sum;
        this.date = date;
        this.bankCardId = bankCardId;
        this.bankCardName = bankCardName;
        this.bankCardNumber = bankCardNumber;
        this.bankCardEndMonth = bankCardEndMonth;
        this.bankCardEndYear = bankCardEndYear;
    }

    public Payment toPayment() {
        BankCard bankCard = new BankCard(bankCardId, bankCardName, bankCardNumber, bankCardEndMonth, bankCardEndYear);
        if (date == null) {
            date = new Date();
        }
        return new Payment(id, sum, date, bankCard);
    }

    public static PaymentDto fromPayment(Payment payment) {
        BankCard bankCard = payment.getBankCard();
        return new PaymentDto(
                payment.getId(),
                payment.getSum(),
                payment.getDate(),
                bankCard.getId(),
                bankCard.getName(),
                bankCard.getCardNumber(),
                bankCard.getEndMonth(),
                bankCard.getEndYear());
    }
}
