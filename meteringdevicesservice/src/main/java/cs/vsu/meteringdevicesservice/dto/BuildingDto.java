package cs.vsu.meteringdevicesservice.dto;

import cs.vsu.meteringdevicesservice.entity.Building;
import lombok.Data;

@Data
public class BuildingDto {
    private Long id;
    private String number;
    private String street;
    private String city;

    public BuildingDto() {
    }

    public BuildingDto(Long id, String number, String street, String city) {
        this.id = id;
        this.number = number;
        this.street = street;
        this.city = city;
    }

    public Building toBuilding() {
        return new Building(id, number, street, city);
    }

    public static BuildingDto fromBuilding(Building building) {
        return new BuildingDto(
                building.getId(),
                building.getNumber(),
                building.getStreet(),
                building.getCity());
    }
}
