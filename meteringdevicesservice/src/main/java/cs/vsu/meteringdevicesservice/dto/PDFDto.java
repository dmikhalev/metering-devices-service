package cs.vsu.meteringdevicesservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class PDFDto {
    private List<byte[]> pdfs;
}
