package cs.vsu.meteringdevicesservice.dto;

import cs.vsu.meteringdevicesservice.entity.Apartment;
import lombok.Data;

@Data
public class ApartmentDto {
    private Long id;
    private Integer number;
    private Integer area;
    private Long buildingId;

    public ApartmentDto(Long id, Integer number, Integer area, Long buildingId) {
        this.id = id;
        this.number = number;
        this.area = area;
        this.buildingId = buildingId;
    }

    public ApartmentDto() {

    }

    public Apartment toApartment() {
        return new Apartment(id, number, area);
    }

    public static ApartmentDto fromApartment(Apartment apartment) {
        return new ApartmentDto(apartment.getId(),
                apartment.getNumber(),
                apartment.getArea(),
                apartment.getBuilding().getId());
    }
}
