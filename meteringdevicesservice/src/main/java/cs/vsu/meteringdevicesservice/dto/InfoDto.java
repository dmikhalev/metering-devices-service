package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cs.vsu.meteringdevicesservice.entity.Info;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoDto {
    private Long id;
    private String name;
    private String text;

    public InfoDto() {

    }

    public InfoDto(Long id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public Info toInfo() {
        return new Info(id, name, text);
    }

    public static InfoDto fromInfo(Info info) {
        return new InfoDto(info.getId(), info.getName(), info.getText());
    }
}
