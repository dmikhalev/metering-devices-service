package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPageDto {
    private String username;
    private String address;

    private Long gasPersonalCode;
    private String gasExecutor;

    private Long waterPersonalCode;
    private String waterExecutor;

    private Long electPersonalCode;
    private String electExecutor;
}
