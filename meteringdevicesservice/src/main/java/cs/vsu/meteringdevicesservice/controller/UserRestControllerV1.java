package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.DebtDto;
import cs.vsu.meteringdevicesservice.dto.PaymentHistoryDto;
import cs.vsu.meteringdevicesservice.dto.UserPageDto;
import cs.vsu.meteringdevicesservice.entity.*;
import cs.vsu.meteringdevicesservice.security.jwt.JwtUser;
import cs.vsu.meteringdevicesservice.service.ReceiptDataService;
import cs.vsu.meteringdevicesservice.service.ReceiptService;
import cs.vsu.meteringdevicesservice.service.ServiceService;
import cs.vsu.meteringdevicesservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserRestControllerV1 {

    private static final String GAS = "gas";
    private static final String WATER = "water";
    private static final String ELECTRICITY = "electricity";

    private final UserService userService;
    private final ReceiptService receiptService;
    private final ReceiptDataService receiptDataService;

    @Autowired
    public UserRestControllerV1(UserService userService, ReceiptService receiptService, ReceiptDataService receiptDataService) {
        this.userService = userService;
        this.receiptService = receiptService;
        this.receiptDataService = receiptDataService;
    }

    @GetMapping()
    public ResponseEntity<UserPageDto> getAuthorizedUser() {
        User user = findAuthorizedUser();
        if (user == null) {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserPageDto result = getUserPageDto(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private UserPageDto getUserPageDto(User user) {
        UserPageDto result = new UserPageDto();
        String address = userService.buildUserAddress(user);
        result.setAddress(address);
        Apartment apartment = user.getApartment();
        Building building = apartment.getBuilding();
        List<ReceiptData> receiptDataList = receiptDataService.findAllByBuildingId(building.getId());
        receiptDataList.forEach(rd -> {
            String service = rd.getTariff().getService().getName();
            String executorName = rd.getExecutor().getName();
            Long personalCode = receiptService.findPersonalCodeByReceiptDataAndApartment(rd.getId(), apartment.getId());
            if (GAS.equalsIgnoreCase(service)) {
                result.setGasExecutor(executorName);
                result.setGasPersonalCode(personalCode);
            } else if (WATER.equalsIgnoreCase(service)) {
                result.setWaterExecutor(executorName);
                result.setWaterPersonalCode(personalCode);
            } else if (ELECTRICITY.equalsIgnoreCase(service)) {
                result.setElectExecutor(executorName);
                result.setElectPersonalCode(personalCode);
            }
        });
        return result;
    }

    @GetMapping(value = "/debt")
    public ResponseEntity<DebtDto> getUnpaidReceipts() {
        User user = findAuthorizedUser();
        if (user == null) {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        DebtDto result = getDebtDto(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private DebtDto getDebtDto(User user) {
        DebtDto result = new DebtDto();
        List<Receipt> unpaidReceipts = receiptService.findUnpaidReceiptsByUserId(user.getId());
        int gas = 0;
        int water = 0;
        int elect = 0;
        for (Receipt r : unpaidReceipts) {
            String service = r.getReceiptData().getTariff().getService().getName();
            if (GAS.equalsIgnoreCase(service)) {
                gas++;
            } else if (WATER.equalsIgnoreCase(service)) {
                water++;
            } else if (ELECTRICITY.equalsIgnoreCase(service)) {
                elect++;
            }
        }
        result.setGasDebt(gas);
        result.setWaterDebt(water);
        result.setElectDebt(elect);
        return result;
    }

    @GetMapping(value = "/payment_history")
    public ResponseEntity<List<PaymentHistoryDto>> getPaidReceipts() {
        User user = findAuthorizedUser();
        if (user == null) {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<PaymentHistoryDto> result = getPaymentHistoryDtos(user);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    private List<PaymentHistoryDto> getPaymentHistoryDtos(User user) {
        List<Receipt> paidReceipts = receiptService.findPaidReceiptsByUserId(user.getId());
        List<PaymentHistoryDto> result = new ArrayList<>();
        paidReceipts.forEach(r -> {
            PaymentHistoryDto dto = new PaymentHistoryDto();
            dto.setService(ServiceService.getServiceName(r.getReceiptData().getTariff().getService().getName()));
            dto.setSum(String.format("%.2f", r.getPayment().getSum()));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = formatter.format(r.getDate());
            dto.setDate(strDate);
            result.add(dto);
        });
        return result;
    }

    private User findAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            log.error("Authorized user not found.");
            return null;
        }
        if (auth.getPrincipal() instanceof JwtUser) {
            return userService.findById(((JwtUser) auth.getPrincipal()).getId());
        }
        return null;
    }
}
