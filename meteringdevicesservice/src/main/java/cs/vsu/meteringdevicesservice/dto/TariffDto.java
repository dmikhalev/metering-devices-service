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

    private Long serviceId;
    private String serviceName;

    public TariffDto() {

    }

    public TariffDto(Long id, Double cost, Date date, Long serviceId, String serviceName) {
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public Tariff toTariff() {
        if (date == null) {
            date = new java.util.Date();
        }
        Service service = new Service(serviceId, serviceName);
        return new Tariff(id, cost, date, service);
    }

    public static TariffDto fromTariff(Tariff tariff) {
        Service service = tariff.getService();
        return new TariffDto(tariff.getId(),
                tariff.getCost(),
                tariff.getDate(),
                service.getId(),
                service.getName());
    }
}
