package cs.vsu.meteringdevicesservice.dto;

import cs.vsu.meteringdevicesservice.entity.Executor;
import lombok.Data;

@Data
public class ExecutorDto {
    private Long id;
    private String name;
    private String address;
    private Long taxId;
    private String phoneNumber;

    public ExecutorDto() {

    }

    public ExecutorDto(Long id, String name, String address, Long taxId, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.taxId = taxId;
        this.phoneNumber = phoneNumber;
    }

    public Executor toExecutor() {
        return new Executor(id, name, address, taxId, phoneNumber);
    }

    public static ExecutorDto fromExecutor(Executor executor) {
        return new ExecutorDto(executor.getId(),
                executor.getName(),
                executor.getAddress(),
                executor.getTaxId(),
                executor.getPhoneNumber());
    }
}
