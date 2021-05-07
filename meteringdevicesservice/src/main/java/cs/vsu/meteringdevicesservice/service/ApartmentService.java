package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Apartment findByBuildingIdAndNumber(Long buildingId, Integer number) throws NotFoundException {
        return apartmentRepository.findByBuilding_IdAndNumber(buildingId, number).orElseThrow(NotFoundException::new);
    }

    public Apartment findById(Long id) throws NotFoundException {
        return apartmentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Apartment> findAllByBuilding(Long buildingId) {
        return new ArrayList<>(apartmentRepository.findAllByBuilding_Id(buildingId));
    }

    public List<Apartment> findAll() {
        return new ArrayList<>(apartmentRepository.findAll());
    }

    public Apartment createOrUpdate(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public void delete(Long id) {
        apartmentRepository.deleteById(id);
    }

}
