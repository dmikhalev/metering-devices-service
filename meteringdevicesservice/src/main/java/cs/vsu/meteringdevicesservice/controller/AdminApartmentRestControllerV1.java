package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.ApartmentDto;
import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.service.ApartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/apartment")
public class AdminApartmentRestControllerV1 {

    private final ApartmentService apartmentService;

    @Autowired
    public AdminApartmentRestControllerV1(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
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
        ApartmentDto result = ApartmentDto.fromApartment(apartment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ApartmentDto>> getAllApartments() {
        List<Apartment> apartments = apartmentService.findAll();
        List<ApartmentDto> result = apartments.stream()
                .map(ApartmentDto::fromApartment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createApartment(@RequestBody ApartmentDto apartmentDto) {
        if (apartmentDto != null) {
            apartmentService.createOrUpdate(apartmentDto.toApartment());
        }
    }

    @PutMapping()
    public void editApartment(@RequestBody ApartmentDto apartmentDto) {
        if (apartmentDto != null) {
            try {
                apartmentService.findById(apartmentDto.getId());
            } catch (NotFoundException e) {
                log.error("Apartment not found.", e);
                return;
            }
            apartmentService.createOrUpdate(apartmentDto.toApartment());
        }
    }

    @DeleteMapping()
    public void deleteApartment(@RequestBody IdDto id) {
        apartmentService.delete(id.getId());
    }
}
