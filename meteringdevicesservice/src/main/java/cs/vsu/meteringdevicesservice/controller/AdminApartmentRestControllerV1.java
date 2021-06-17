package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.ApartmentDto;
import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.entity.Building;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.service.ApartmentService;
import cs.vsu.meteringdevicesservice.service.BuildingService;
import cs.vsu.meteringdevicesservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/apartment")
public class AdminApartmentRestControllerV1 {

    private final ApartmentService apartmentService;
    private final BuildingService buildingService;
    private final UserService userService;

    @Autowired
    public AdminApartmentRestControllerV1(ApartmentService apartmentService, BuildingService buildingService, UserService userService) {
        this.apartmentService = apartmentService;
        this.buildingService = buildingService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<ApartmentDto> getApartmentById(@RequestBody IdDto id) {
        Apartment apartment;
        try {
            apartment = apartmentService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("Apartment not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ApartmentDto> result = toApartmentDtos(Collections.singletonList(apartment));
        return new ResponseEntity<>(result.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ApartmentDto>> getAllApartments() {
        List<Apartment> apartments = apartmentService.findAll();
        List<ApartmentDto> result = toApartmentDtos(apartments);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createOrUpdateApartment(@RequestBody ApartmentDto apartmentDto) {
        if (apartmentDto != null) {
            Long apartmentId = apartmentDto.getApartmentId();
            Apartment apartment = null;
            if (apartmentId != null) {
                apartment = apartmentService.findById(apartmentId);
            }
            boolean existsApartment = false;
            if (apartment == null) {
                existsApartment = true;
            }
            Building building = buildingService.findByCityAndStreetAndNumber(apartmentDto.getBuildingCity(), apartmentDto.getBuildingStreet(), apartmentDto.getBuildingNumber());
            if (building == null) {
                building = new Building(apartmentDto.getBuildingNumber(), apartmentDto.getBuildingStreet(), apartmentDto.getBuildingCity());
                buildingService.createOrUpdate(building);
            }
            User user = userService.findByUsername(apartmentDto.getUsername());
            apartment = apartmentDto.toApartment();
            apartment.setBuilding(building);
            if (user != null) {
                apartment.setUser(user);
            }
            apartment = apartmentService.createOrUpdate(apartment);
            if (existsApartment) {
                apartmentService.createFirstReceipts(apartment, apartmentDto.getGasLastValue(), apartmentDto.getWaterLastValue(), apartmentDto.getElectroLastValue());
            }
        }
    }

    @PostMapping(value = "/add_user")
    public void addUserToApartment(@RequestBody ApartmentDto apartmentDto) {
        if (apartmentDto != null) {
            User user = userService.findByUsername(apartmentDto.getUsername());
            Apartment apartment = apartmentDto.toApartment();
            apartment.setUser(user);
            apartmentService.createOrUpdate(apartment);
        }
    }

    @DeleteMapping()
    public void deleteApartment(@RequestBody IdDto id) {
        apartmentService.delete(id.getId());
    }

    private List<ApartmentDto> toApartmentDtos(List<Apartment> apartments) {
        final List<ApartmentDto> result = new ArrayList<>();
        apartments.forEach(a -> {
            ApartmentDto apartmentDto = ApartmentDto.fromApartment(a);
            apartmentDto.setGas(a.getGasCode());
            apartmentDto.setWater(a.getWaterCode());
            apartmentDto.setElectro(a.getElectricityCode());
            result.add(apartmentDto);
        });
        return result;
    }
}
