package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.ChangePasswordDto;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static cs.vsu.meteringdevicesservice.service.ServiceService.ServiceName.GAS;
import static cs.vsu.meteringdevicesservice.service.ServiceService.ServiceName.WATER;
import static cs.vsu.meteringdevicesservice.service.ServiceService.ServiceName.ELECTRICITY;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserRestControllerV1 {

    private final UserService userService;
    private final ReceiptService receiptService;
    private final ReceiptDataService receiptDataService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRestControllerV1(UserService userService, ReceiptService receiptService, ReceiptDataService receiptDataService,
                                @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.receiptService = receiptService;
        this.receiptDataService = receiptDataService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public ResponseEntity<UserPageDto> getAuthorizedUser() {
        User user = findAuthorizedUser();
        if (user == null) {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserPageDto result = getUserPageDto(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/change_pass")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        User user = findAuthorizedUser();
        if (user == null) {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Map<String, String> response = new HashMap<>();
        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userService.createOrUpdate(user);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        }
        response.put("status", "error");
        return ResponseEntity.of(Optional.of(response));
    }

    private UserPageDto getUserPageDto(User user) {
        UserPageDto result = new UserPageDto();
        String address = userService.buildUserAddress(user);
        result.setAddress(address);
        Apartment apartment = user.getApartment();
        Building building = apartment.getBuilding();
        List<ReceiptData> receiptDataList = receiptDataService.findAllByBuildingId(building.getId());
        result.setGasPersonalCode(apartment.getGasCode());
        result.setWaterPersonalCode(apartment.getWaterCode());
        result.setElectPersonalCode(apartment.getElectricityCode());
        receiptDataList.forEach(rd -> {
            String service = rd.getTariff().getService().getName();
            String executorName = rd.getExecutor().getName();
            if (GAS.name().equalsIgnoreCase(service)) {
                result.setGasExecutor(executorName);
            } else if (WATER.name().equalsIgnoreCase(service)) {
                result.setWaterExecutor(executorName);
            } else if (ELECTRICITY.name().equalsIgnoreCase(service)) {
                result.setElectExecutor(executorName);
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
        Date now = new Date();
        for (Receipt r : unpaidReceipts) {
            String service = r.getReceiptData().getTariff().getService().getName();
            if (GAS.name().equalsIgnoreCase(service)) {
                gas = 1 + getMonthsBetween(r.getDate(), now);
            } else if (WATER.name().equalsIgnoreCase(service)) {
                water = 1 + getMonthsBetween(r.getDate(), now);
            } else if (ELECTRICITY.name().equalsIgnoreCase(service)) {
                elect = 1 + getMonthsBetween(r.getDate(), now);
            }
        }
        result.setGasDebt(gas);
        result.setWaterDebt(water);
        result.setElectDebt(elect);
        return result;
    }

    public int getMonthsBetween(Date date1, Date date2) {
        return (int) ((Math.max(date1.getTime(), date2.getTime()) - Math.min(date1.getTime(), date2.getTime())) / (1000 * 60 * 60 * 24) / 30);
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
