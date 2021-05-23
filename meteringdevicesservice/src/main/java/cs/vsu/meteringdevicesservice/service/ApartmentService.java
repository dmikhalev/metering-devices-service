package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.entity.ReceiptData;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cs.vsu.meteringdevicesservice.service.ServiceService.ServiceName.*;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ReceiptDataService receiptDataService;
    private final ReceiptService receiptService;

    public ApartmentService(ApartmentRepository apartmentRepository, ReceiptDataService receiptDataService, ReceiptService receiptService) {
        this.apartmentRepository = apartmentRepository;
        this.receiptDataService = receiptDataService;
        this.receiptService = receiptService;
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

    public Map<ServiceService.ServiceName, Long> getPersonalCodes(Apartment apartment) {
        Map<ServiceService.ServiceName, Long> result = new HashMap<>();
        List<ReceiptData> receiptDataList = receiptDataService.findAllByBuildingId(apartment.getBuilding().getId());
        receiptDataList.forEach(rd -> {
            String service = rd.getTariff().getService().getName();
            Long personalCode = null;
            try {
                personalCode = receiptService.findPersonalCodeByReceiptDataAndApartment(rd.getId(), apartment.getId());
                if (GAS.name().toLowerCase().equalsIgnoreCase(service)) {
                    result.put(GAS, personalCode);
                } else if (WATER.name().toLowerCase().equalsIgnoreCase(service)) {
                    result.put(WATER, personalCode);
                } else if (ELECTRICITY.name().toLowerCase().equalsIgnoreCase(service)) {
                    result.put(ELECTRICITY, personalCode);
                }
            } catch (NotFoundException e) {
            }
        });
        return result;
    }
}
