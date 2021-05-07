package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Building;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public Building findById(Long id) throws NotFoundException {
        return buildingRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Building> findAll() {
        return new ArrayList<>(buildingRepository.findAll());
    }

    public List<Building> findAllByPostcode(long postcode) {
        return new ArrayList<>(buildingRepository.findAllByPostcode(postcode));
    }

    public Building createOrUpdate(Building building) {
        return buildingRepository.save(building);
    }

    public void delete(Long id) {
        buildingRepository.deleteById(id);
    }
}
