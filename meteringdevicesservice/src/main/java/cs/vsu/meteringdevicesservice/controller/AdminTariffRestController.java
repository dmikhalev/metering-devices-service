package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.TariffDto;
import cs.vsu.meteringdevicesservice.entity.Tariff;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.service.ServiceService;
import cs.vsu.meteringdevicesservice.service.TariffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/tariff")
public class AdminTariffRestController {

    private static final String GAS = ServiceService.ServiceName.GAS.name().toLowerCase();
    private static final String WATER = ServiceService.ServiceName.WATER.name().toLowerCase();
    private static final String ELECTRICITY = ServiceService.ServiceName.ELECTRICITY.name().toLowerCase();

    private final TariffService tariffService;

    public AdminTariffRestController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping()
    public ResponseEntity<TariffDto> getTariffById(@RequestBody IdDto id) {
        Tariff tariff;
        try {
            tariff = tariffService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("Tariff not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        TariffDto result = TariffDto.fromTariff(tariff);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/active")
    public ResponseEntity getActiveTariffs() {
        List<Tariff> tariffs = tariffService.getActiveTariffs();
        if (tariffs == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<String, Double> response = new HashMap<>();
        response.put(GAS, null);
        response.put(WATER, null);
        response.put(ELECTRICITY, null);
        tariffs.forEach(t -> {
            String service = t.getService().getName();
            if (GAS.equalsIgnoreCase(service)) {
                response.put(GAS, t.getCost());
            } else if (WATER.equalsIgnoreCase(service)) {
                response.put(WATER, t.getCost());
            } else if (ELECTRICITY.equalsIgnoreCase(service)) {
                response.put(ELECTRICITY, t.getCost());
            }
        });
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public void createTariff(@RequestBody TariffDto tariffDto) {
        if (tariffDto != null) {
            tariffService.createOrUpdate(tariffDto.toTariff());
        }
    }

    @PutMapping()
    public void editTariff(@RequestBody TariffDto tariffDto) {
        if (tariffDto != null) {
            try {
                tariffService.findById(tariffDto.getId());
            } catch (NotFoundException e) {
                log.error("Tariff not found.", e);
                return;
            }
            tariffService.createOrUpdate(tariffDto.toTariff());
        }
    }

    @DeleteMapping()
    public void deleteTariff(@RequestBody IdDto id) {
        tariffService.delete(id.getId());
    }
}
