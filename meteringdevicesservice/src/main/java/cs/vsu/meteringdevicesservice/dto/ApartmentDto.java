package cs.vsu.meteringdevicesservice.dto;

import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.entity.Building;
import lombok.Data;

@Data
public class ApartmentDto {
    private Long apartmentId;
    private Integer apartmentNumber;
    private Long buildingId;
    private String buildingCity;
    private String buildingStreet;
    private String buildingNumber;
    private Long gas;
    private Long water;
    private Long electro;

    public ApartmentDto(Long apartmentId, Integer apartmentNumber, Long buildingId, String buildingCity, String buildingStreet, String buildingNumber) {
        this.apartmentId = apartmentId;
        this.apartmentNumber = apartmentNumber;
        this.buildingId = buildingId;
        this.buildingCity = buildingCity;
        this.buildingStreet = buildingStreet;
        this.buildingNumber = buildingNumber;
    }

    public ApartmentDto() {

    }

    public Apartment toApartment() {
        Building building = new Building();
        Apartment apartment = new Apartment(apartmentId, apartmentNumber);
        apartment.setBuilding(building);
        return apartment;
    }

    public static ApartmentDto fromApartment(Apartment apartment) {
        Building building = apartment.getBuilding();
        return new ApartmentDto(apartment.getId(),
                apartment.getNumber(),
                building.getId(),
                building.getCity(),
                building.getStreet(),
                building.getNumber());
    }
}
