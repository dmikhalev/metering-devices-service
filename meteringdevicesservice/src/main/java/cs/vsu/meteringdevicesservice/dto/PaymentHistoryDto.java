package cs.vsu.meteringdevicesservice.dto;

import lombok.Data;

@Data
public class PaymentHistoryDto {
    String service;
    String date;
    String sum;
}
