package cs.vsu.meteringdevicesservice.dto;

import cs.vsu.meteringdevicesservice.entity.Service;
import cs.vsu.meteringdevicesservice.entity.Tariff;
import lombok.Data;

import java.util.Date;

@Data
public class TariffDto {

    private Long id;
    private Double cost;
    private Date date;
    private String serviceName;

    public TariffDto() {

    }

    public TariffDto(Long id, Double cost, Date date, String serviceName) {
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.serviceName = serviceName;
    }

    public Tariff toTariff() {
        if (date == null) {
            date = new java.util.Date();
        }
        return new Tariff(cost, date);
    }

    public static TariffDto fromTariff(Tariff tariff) {
        Service service = tariff.getService();
        return new TariffDto(tariff.getId(),
                tariff.getCost(),
                tariff.getDate(),
                service.getName());
    }
}
