package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cs.vsu.meteringdevicesservice.entity.BankCard;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankCardDto {
    private Long id;
    private String name;
    private Long cardNumber;
    private Integer endMonth;
    private Integer endYear;

    public BankCardDto() {
    }

    public BankCardDto(Long id, String name, Long cardNumber, Integer endMonth, Integer endYear) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }

    public BankCard toBankCard() {
        return new BankCard(id, name, cardNumber, endMonth, endYear);
    }

    public static BankCardDto fromBankCard(BankCard bankCard) {
        return new BankCardDto(
                bankCard.getId(),
                bankCard.getName(),
                bankCard.getCardNumber(),
                bankCard.getEndMonth(),
                bankCard.getEndMonth());
    }

}
