package cs.vsu.meteringdevicesservice.dto;

import lombok.Data;

@Data
public class IdDto {

    private long id;

    public IdDto() {
    }

    public IdDto(long id) {
        this.id = id;
    }

}
