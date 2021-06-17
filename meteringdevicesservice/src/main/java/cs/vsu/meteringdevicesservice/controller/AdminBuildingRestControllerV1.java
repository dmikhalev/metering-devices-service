package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.ApartmentDto;
import cs.vsu.meteringdevicesservice.dto.BuildingDto;
import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.entity.Building;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.service.BuildingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/building")
public class AdminBuildingRestControllerV1 {

    private final BuildingService buildingService;

    @Autowired
    public AdminBuildingRestControllerV1(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping()
    public ResponseEntity<BuildingDto> getBuildingById(@RequestBody IdDto id) {
        Building building;
        try {
            building = buildingService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("Building not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        BuildingDto result = BuildingDto.fromBuilding(building);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<BuildingDto>> getAllBuildings() {
        List<Building> buildings = buildingService.findAll();
        List<BuildingDto> result = buildings.stream()
                .map(BuildingDto::fromBuilding)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "postcode/{postcode}")
    public ResponseEntity<List<BuildingDto>> getAllBuildings(@PathVariable Long postcode) {
        List<Building> buildings = buildingService.findAllByPostcode(postcode);
        List<BuildingDto> result = buildings.stream()
                .map(BuildingDto::fromBuilding)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/apartments")
    public ResponseEntity<List<ApartmentDto>> getAllApartmentsByBuildingId(@RequestBody IdDto id) {
        Building building;
        try {
            building = buildingService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("Building not found", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ApartmentDto> result = building.getApartments().stream()
                .map(ApartmentDto::fromApartment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createBuilding(@RequestBody BuildingDto buildingDto) {
        if (buildingDto != null) {
            buildingService.createOrUpdate(buildingDto.toBuilding());
        }
    }

    @PutMapping()
    public void editBuilding(@RequestBody BuildingDto buildingDto) {
        if (buildingDto != null) {
            try {
                buildingService.findById(buildingDto.getId());
            } catch (NotFoundException e) {
                log.error("Building not found.", e);
                return;
            }
            buildingService.createOrUpdate(buildingDto.toBuilding());
        }
    }

    @DeleteMapping()
    public void deleteBuilding(@RequestBody IdDto id) {
        buildingService.delete(id.getId());
    }
}
