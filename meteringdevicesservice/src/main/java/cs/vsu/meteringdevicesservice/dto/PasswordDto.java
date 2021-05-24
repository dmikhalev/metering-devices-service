package cs.vsu.meteringdevicesservice.dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String password;

    public PasswordDto() {
    }

    public PasswordDto(String password) {
        this.password = password;
    }


}
