package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.*;
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
    private final TariffService tariffService;
    private final ExecutorService executorService;

    public ApartmentService(ApartmentRepository apartmentRepository, ReceiptDataService receiptDataService,
                            ReceiptService receiptService, TariffService tariffService, ExecutorService executorService) {
        this.apartmentRepository = apartmentRepository;
        this.receiptDataService = receiptDataService;
        this.receiptService = receiptService;
        this.tariffService = tariffService;
        this.executorService = executorService;
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

    public Long getPersonalCodeByServiceName(Apartment apartment, String serviceName) {
        if (serviceName.equalsIgnoreCase(WATER.name())) {
            return apartment.getWaterCode();
        }
        if (serviceName.equalsIgnoreCase(GAS.name())) {
            return apartment.getGasCode();
        }
        if (serviceName.equalsIgnoreCase(ELECTRICITY.name())) {
            return apartment.getElectricityCode();
        }
        return null;
    }

    public Apartment getApartmentByWaterCode(long code) {
        return apartmentRepository.findApartmentByWaterCode(code).orElse(null);
    }

    public Apartment getApartmentByGasCode(long code) {
        return apartmentRepository.findApartmentByGasCode(code).orElse(null);
    }

    public Apartment getApartmentByElectricityCode(long code) {
        return apartmentRepository.findApartmentByElectricityCode(code).orElse(null);
    }

    public synchronized void createFirstReceipts(Apartment apartment, long gasLastValue, long waterLastValue, long electroLastValue) {
        Map<ServiceService.ServiceName, ReceiptData> receiptDatas = receiptDataService.getMappedReceiptDatas().get(apartment.getBuilding().getId());
        if (receiptDatas == null || receiptDatas.isEmpty()) {
            receiptDatas = new HashMap<>();
            List<Tariff> tariffs = tariffService.getActiveTariffs();
            List<Executor> executors = executorService.findAll();
            Map<ServiceService.ServiceName, ReceiptData> finalReceiptDatas = receiptDatas;
            tariffs.forEach(t -> {
                if (t.getService().getName().equalsIgnoreCase(WATER.name())) {
                    ReceiptData rd = new ReceiptData(t, apartment.getBuilding(), executors.get(0));
                    rd = receiptDataService.createOrUpdate(rd);
                    finalReceiptDatas.put(WATER, rd);
                } else if (t.getService().getName().equalsIgnoreCase(GAS.name())) {
                    ReceiptData rd = new ReceiptData(t, apartment.getBuilding(), executors.get(1));
                    rd = receiptDataService.createOrUpdate(rd);
                    finalReceiptDatas.put(GAS, rd);
                } else if (t.getService().getName().equalsIgnoreCase(ELECTRICITY.name())) {
                    ReceiptData rd = new ReceiptData(t, apartment.getBuilding(), executors.get(2));
                    rd = receiptDataService.createOrUpdate(rd);
                    finalReceiptDatas.put(ELECTRICITY, rd);
                }
            });
        }
        Receipt waterReceipt = new Receipt(waterLastValue, apartment.getWaterCode(), receiptDatas.get(WATER), apartment);
        receiptService.createOrUpdate(waterReceipt);
        Receipt gasReceipt = new Receipt(gasLastValue, apartment.getGasCode(), receiptDatas.get(GAS), apartment);
        receiptService.createOrUpdate(gasReceipt);
        Receipt electricityReceipt = new Receipt(electroLastValue, apartment.getElectricityCode(), receiptDatas.get(ELECTRICITY), apartment);
        receiptService.createOrUpdate(electricityReceipt);
    }
}
