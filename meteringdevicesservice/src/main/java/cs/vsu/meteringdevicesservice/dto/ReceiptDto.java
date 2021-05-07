package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cs.vsu.meteringdevicesservice.entity.*;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiptDto {
    private Long receiptId;
    private Long receiptPrevAmount;
    private Long receiptCurrAmount;
    private Date receiptDate;
    private Long receiptPersonalCode;

    private Long tariffId;
    private Double tariffCost;
    private Date tariffDate;

    private Long serviceId;
    private String serviceName;

    private Long buildingId;
    private String buildingNumber;
    private String buildingStreet;
    private String buildingCity;
    private Long buildingPostcode;

    private Long executorId;
    private String executorName;
    private String executorAddress;
    private Long executorTaxId;
    private String executorPhoneNumber;

    private Long receiptDataId;

    private Long apartmentId;
    private Integer apartmentNumber;

    private Long paymentId;
    private Double paymentSum;
    private Date paymentDate;

    public Receipt toReceipt() {
        Service service = new Service(serviceId, serviceName);
        Tariff tariff = new Tariff(tariffId, tariffCost, tariffDate, service);
        Building building = new Building(buildingId, buildingNumber, buildingStreet, buildingCity, buildingPostcode);
        Executor executor = new Executor(executorId, executorName, executorAddress, executorTaxId, executorPhoneNumber);
        ReceiptData receiptData = new ReceiptData(receiptDataId, tariff, building, executor);
        Apartment apartment = new Apartment(apartmentId, apartmentNumber);

        Receipt receipt = new Receipt();
        receipt.setApartment(apartment);
        receipt.setReceiptData(receiptData);
        receipt.setPrevAmount(receiptPrevAmount);
        receipt.setCurrAmount(receiptCurrAmount);
        receipt.setPersonalCode(receiptPersonalCode);
        receipt.setDate(receiptDate);
        if (paymentId != null) {
            Payment payment = new Payment(paymentId, paymentSum, paymentDate, null);
            receipt.setPayment(payment);
        }

        return receipt;
    }

    public static ReceiptDto fromReceipt(Receipt receipt) {
        ReceiptDto receiptDto = new ReceiptDto();

        receiptDto.setReceiptId(receipt.getId());
        receiptDto.setReceiptPrevAmount(receipt.getPrevAmount());
        receiptDto.setReceiptCurrAmount(receipt.getCurrAmount());
        receiptDto.setReceiptPersonalCode(receiptDto.getReceiptPersonalCode());
        receiptDto.setReceiptDate(receipt.getDate());

        ReceiptData receiptData = receipt.getReceiptData();
        receiptDto.setReceiptDataId(receiptData.getId());

        Tariff tariff = receiptData.getTariff();
        receiptDto.setTariffId(tariff.getId());
        receiptDto.setTariffCost(tariff.getCost());
        receiptDto.setTariffDate(tariff.getDate());

        Service service = tariff.getService();
        receiptDto.setServiceId(service.getId());
        receiptDto.setServiceName(service.getName());

        Executor executor = receiptData.getExecutor();
        receiptDto.setExecutorId(executor.getId());
        receiptDto.setExecutorAddress(executor.getAddress());
        receiptDto.setExecutorName(executor.getName());
        receiptDto.setExecutorPhoneNumber(executor.getPhoneNumber());
        receiptDto.setExecutorTaxId(executor.getTaxId());

        Building building = receiptData.getBuilding();
        receiptDto.setBuildingId(building.getId());
        receiptDto.setBuildingCity(building.getCity());
        receiptDto.setBuildingStreet(building.getStreet());
        receiptDto.setBuildingNumber(building.getNumber());
        receiptDto.setBuildingPostcode(building.getPostcode());

        Apartment apartment = receipt.getApartment();
        receiptDto.setApartmentId(apartment.getId());
        receiptDto.setApartmentNumber(apartment.getNumber());

        if (receipt.getPayment() != null) {
            Payment payment = receipt.getPayment();
            receiptDto.setPaymentId(payment.getId());
            receiptDto.setPaymentSum(payment.getSum());
            receiptDto.setPaymentDate(payment.getDate());
        }
        return receiptDto;
    }
}
