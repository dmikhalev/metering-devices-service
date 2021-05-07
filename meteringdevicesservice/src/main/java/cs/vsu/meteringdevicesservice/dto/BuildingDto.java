package cs.vsu.meteringdevicesservice.dto;

import cs.vsu.meteringdevicesservice.entity.Building;
import lombok.Data;

@Data
public class BuildingDto {
    private Long id;
    private String number;
    private String street;
    private String city;
    private Long postcode;

    public BuildingDto() {
    }

    public BuildingDto(Long id, String number, String street, String city, Long postcode) {
        this.id = id;
        this.number = number;
        this.street = street;
        this.city = city;
        this.postcode = postcode;
    }

    public Building toBuilding() {
        return new Building(id, number, street, city, postcode);
    }

    public static BuildingDto fromBuilding(Building building) {
        return new BuildingDto(
                building.getId(),
                building.getNumber(),
                building.getStreet(),
                building.getCity(),
                building.getPostcode());
    }
}
