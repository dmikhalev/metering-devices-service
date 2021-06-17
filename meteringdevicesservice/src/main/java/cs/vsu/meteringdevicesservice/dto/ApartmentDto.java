package cs.vsu.meteringdevicesservice.dto;

import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.entity.Building;
import cs.vsu.meteringdevicesservice.entity.User;
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
    private String username;

   private Long gasLastValue;
   private Long waterLastValue;
   private Long electroLastValue;

    public ApartmentDto(Long apartmentId, Integer apartmentNumber, Long buildingId, String buildingCity,
                        String buildingStreet, String buildingNumber, String username) {
        this.apartmentId = apartmentId;
        this.apartmentNumber = apartmentNumber;
        this.buildingId = buildingId;
        this.buildingCity = buildingCity;
        this.buildingStreet = buildingStreet;
        this.buildingNumber = buildingNumber;
        this.username = username;
    }

    public ApartmentDto() {

    }

    public Apartment toApartment() {
        Building building = new Building(buildingId, buildingNumber, buildingStreet, buildingCity);
        Apartment apartment = new Apartment(apartmentId, apartmentNumber, water, gas, electro);
        apartment.setBuilding(building);
        return apartment;
    }

    public static ApartmentDto fromApartment(Apartment apartment) {
        User user = apartment.getUser();
        String username = user == null ? "" : user.getLogin();
        Building building = apartment.getBuilding();
        return new ApartmentDto(apartment.getId(),
                apartment.getNumber(),
                building.getId(),
                building.getCity(),
                building.getStreet(),
                building.getNumber(),
                username);
    }
}
