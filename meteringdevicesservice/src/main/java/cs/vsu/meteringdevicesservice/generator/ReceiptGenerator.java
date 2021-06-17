package cs.vsu.meteringdevicesservice.generator;

import cs.vsu.meteringdevicesservice.entity.*;
import cs.vsu.meteringdevicesservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static cs.vsu.meteringdevicesservice.service.ServiceService.ServiceName.*;

@Component
public class ReceiptGenerator {

    private final ApartmentService apartmentService;
    private final ReceiptService receiptService;
    private final ReceiptDataService receiptDataService;

    @Autowired
    public ReceiptGenerator(ApartmentService apartmentService, ReceiptService receiptService, ReceiptDataService receiptDataService) {
        this.apartmentService = apartmentService;
        this.receiptService = receiptService;
        this.receiptDataService = receiptDataService;
    }

    @Scheduled(cron = "1 1 1 1 * ?")
    public void generateReceipts() {
        Map<Long, Map<ServiceService.ServiceName, ReceiptData>> mappedReceiptDatas = receiptDataService.getMappedReceiptDatas();
        List<Apartment> apartments = apartmentService.findAll();
        apartments.forEach(apartment -> {
            long buildingId = apartment.getBuilding().getId();
            Map<ServiceService.ServiceName, ReceiptData> receiptDataMap = mappedReceiptDatas.get(buildingId);
            if (receiptDataMap != null) {
                createWaterReceipt(apartment, receiptDataMap.get(WATER));
                createGasReceipt(apartment, receiptDataMap.get(GAS));
                createElectricityReceipt(apartment, receiptDataMap.get(ELECTRICITY));
            }
        });
    }

    private void createWaterReceipt(Apartment apartment, ReceiptData receiptData) {
        if (receiptData == null || apartment == null) {
            return;
        }
        Receipt unpaidReceipt = receiptService.findLastUnpaidReceiptBy(WATER.name().toLowerCase(), apartment.getWaterCode());
        if (unpaidReceipt != null) {
            return;
        }
        Receipt prevReceipt = receiptService.findLastPaidReceiptBy(WATER.name().toLowerCase(), apartment.getWaterCode());
        if (prevReceipt != null) {
            Receipt receipt = new Receipt();
            receipt.setPrevAmount(prevReceipt.getCurrAmount());
            receipt.setReceiptData(receiptData);
            receipt.setApartment(apartment);
            receipt.setDate(new Date());
            receipt.setPersonalCode(apartment.getWaterCode());
            receiptService.createOrUpdate(receipt);
        }
    }

    private void createGasReceipt(Apartment apartment, ReceiptData receiptData) {
        if (receiptData == null || apartment == null) {
            return;
        }
        Receipt unpaidReceipt = receiptService.findLastUnpaidReceiptBy(GAS.name().toLowerCase(), apartment.getGasCode());
        if (unpaidReceipt != null) {
            return;
        }
        Receipt prevReceipt = receiptService.findLastPaidReceiptBy(GAS.name().toLowerCase(), apartment.getGasCode());
        if (prevReceipt != null) {
            Receipt receipt = new Receipt();
            receipt.setPrevAmount(prevReceipt.getCurrAmount());
            receipt.setReceiptData(receiptData);
            receipt.setApartment(apartment);
            receipt.setDate(new Date());
            receipt.setPersonalCode(apartment.getGasCode());
            receiptService.createOrUpdate(receipt);
        }
    }

    private void createElectricityReceipt(Apartment apartment, ReceiptData receiptData) {
        if (receiptData == null || apartment == null) {
            return;
        }
        Receipt unpaidReceipt = receiptService.findLastUnpaidReceiptBy(ELECTRICITY.name().toLowerCase(), apartment.getElectricityCode());
        if (unpaidReceipt != null) {
            return;
        }
        Receipt prevReceipt = receiptService.findLastPaidReceiptBy(ELECTRICITY.name().toLowerCase(), apartment.getElectricityCode());
        if (prevReceipt != null) {
            Receipt receipt = new Receipt();
            receipt.setPrevAmount(prevReceipt.getCurrAmount());
            receipt.setReceiptData(receiptData);
            receipt.setApartment(apartment);
            receipt.setDate(new Date());
            receipt.setPersonalCode(apartment.getElectricityCode());
            receiptService.createOrUpdate(receipt);
        }
    }
}
