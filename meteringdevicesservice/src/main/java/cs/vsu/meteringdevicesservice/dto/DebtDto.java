package cs.vsu.meteringdevicesservice.dto;

import lombok.Data;

@Data
public class DebtDto {
    private Integer gasDebt;
    private Integer waterDebt;
    private Integer electDebt;
}
