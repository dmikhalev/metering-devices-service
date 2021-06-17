package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.PaymentDto;
import cs.vsu.meteringdevicesservice.entity.*;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.security.jwt.JwtUser;
import cs.vsu.meteringdevicesservice.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static cs.vsu.meteringdevicesservice.service.ServiceService.ServiceName.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pay")
public class PaymentRestControllerV1 {

    private final PaymentService paymentService;
    private final ReceiptService receiptService;
    private final TariffService tariffService;
    private final UserService userService;
    private final BankCardService bankCardService;
    private final ApartmentService apartmentService;

    @Autowired
    public PaymentRestControllerV1(PaymentService paymentService, ReceiptService receiptService, TariffService tariffService,
                                   UserService userService, BankCardService bankCardService, ApartmentService apartmentService) {
        this.paymentService = paymentService;
        this.receiptService = receiptService;
        this.tariffService = tariffService;
        this.userService = userService;
        this.bankCardService = bankCardService;
        this.apartmentService = apartmentService;
    }

    @GetMapping()
    public ResponseEntity<PaymentDto> getPaymentById(@RequestBody IdDto id) {
        Payment payment;
        try {
            payment = paymentService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("Payment not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PaymentDto result = PaymentDto.fromPayment(payment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/page_1")
    public ResponseEntity getPaymentPage1(@RequestBody PaymentDto paymentDto) {
        User user = findAuthorizedUser();
        if (user != null) {
            Apartment apartment = user.getApartment();
            long personalCode = apartmentService.getPersonalCodeByServiceName(apartment, paymentDto.getServiceName());
            paymentDto.setPersonalCode(personalCode);
            return new ResponseEntity<>(paymentDto, HttpStatus.OK);
        } else {
            Long personalCode = paymentDto.getPersonalCode();
            ServiceService.ServiceName serviceName = ServiceService.ServiceName.findByName(paymentDto.getServiceName());
            Map<Object, Object> response = new HashMap<>();
            if (personalCode != null && personalCode > 0
                    && (serviceName == WATER && apartmentService.getApartmentByWaterCode(personalCode) != null
                    || serviceName == GAS && apartmentService.getApartmentByGasCode(personalCode) != null
                    || serviceName == ELECTRICITY && apartmentService.getApartmentByElectricityCode(personalCode) != null)) {
                response.put("status", "success");
                return ResponseEntity.ok(response);
            }
            response.put("status", "no success");
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping(value = "/page_2")
    public ResponseEntity<PaymentDto> getPaymentPage2(@RequestBody PaymentDto paymentDto) {
        Receipt receipt = receiptService.findLastUnpaidReceiptBy(paymentDto.getServiceName(), paymentDto.getPersonalCode());
        if (receipt != null) {
            paymentDto.setPrevAmount(receipt.getPrevAmount());
            paymentDto.setTariffCost(receipt.getReceiptData().getTariff().getCost());
            return new ResponseEntity<>(paymentDto, HttpStatus.OK);
        } else {
            log.error("Failed to fetch unpaid receipt.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/success")
    public ResponseEntity getPaymentPage3(@RequestBody PaymentDto paymentDto) {
        Map<Object, Object> response = new HashMap<>();
        boolean status = receiptService.payUnpaidReceipt(paymentDto.getServiceName(), paymentDto.getPersonalCode(), paymentDto.getSum(), paymentDto.getCurrAmount());
        response.put("status", status ? "success" : "no success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public void deletePayment(@RequestBody IdDto id) {
        paymentService.delete(id.getId());
    }

    private boolean validBankCardEndDate(int endMonth, int endYear) {
        long currDate = System.currentTimeMillis();
        long cardDate = new GregorianCalendar(2000 + endYear, endMonth - 1, 0).getTimeInMillis();
        return currDate < cardDate;
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
