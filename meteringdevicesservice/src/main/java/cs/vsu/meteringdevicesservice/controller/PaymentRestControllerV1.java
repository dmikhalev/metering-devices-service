package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.PaymentDto;
import cs.vsu.meteringdevicesservice.entity.BankCard;
import cs.vsu.meteringdevicesservice.entity.Payment;
import cs.vsu.meteringdevicesservice.entity.Receipt;
import cs.vsu.meteringdevicesservice.entity.User;
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

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/pay")
public class PaymentRestControllerV1 {

    private final PaymentService paymentService;
    private final ReceiptService receiptService;
    private final TariffService tariffService;
    private final UserService userService;
    private final BankCardService bankCardService;

    @Autowired
    public PaymentRestControllerV1(PaymentService paymentService, ReceiptService receiptService, TariffService tariffService,
                                   UserService userService, BankCardService bankCardService) {
        this.paymentService = paymentService;
        this.receiptService = receiptService;
        this.tariffService = tariffService;
        this.userService = userService;
        this.bankCardService = bankCardService;
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

    @GetMapping(value = "/page_1")
    public ResponseEntity<PaymentDto> getPaymentPage1(@RequestBody PaymentDto paymentDto) {
        Long personalCode = paymentDto.getPersonalCode();
        String serviceName = paymentDto.getServiceName();
        try {
            Receipt currReceipt = receiptService.findLastUnpaidReceiptBy(serviceName, personalCode);
            paymentDto.setPrevAmount(currReceipt.getPrevAmount());
            paymentDto.setReceiptId(currReceipt.getId());
        } catch (NotFoundException e) {
            log.error("Current receipt not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @GetMapping(value = "/page_2")
    public ResponseEntity<PaymentDto> getPaymentPage2(@RequestBody PaymentDto paymentDto) {
        Long prevAmount = paymentDto.getPrevAmount();
        Long currAmount = paymentDto.getCurrAmount();
        if (currAmount < prevAmount) {
            log.error("Incorrect amount input.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Double cost = tariffService.getActiveTariffByService(paymentDto.getServiceName()).getCost();
        Double sum = paymentService.calcPrice(prevAmount, currAmount, cost);
        paymentDto.setSum(sum);
        paymentDto.setTariffCost(cost);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @GetMapping(value = "/page_3")
    public ResponseEntity<PaymentDto> getPaymentPage3(@RequestBody PaymentDto paymentDto) {
        User user = findAuthorizedUser();
        if (user != null) {
            Payment lastPayment = paymentService.findLastPaymentByUserId(user.getId());
            BankCard bankCard = lastPayment.getBankCard();
            paymentDto.setBankCardId(bankCard.getId());
            paymentDto.setBankCardNumber(bankCard.getCardNumber());
            paymentDto.setBankCardEndMonth(bankCard.getEndMonth());
            paymentDto.setBankCardEndYear(bankCard.getEndYear());
            paymentDto.setBankCardName(bankCard.getName());
        }
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @PostMapping(value = "/page_4")
    public ResponseEntity<Boolean> getPaymentPage4(@RequestBody PaymentDto paymentDto) {
        try {
            Payment payment = paymentDto.toPayment();
            BankCard bankCard = new BankCard(paymentDto.getBankCardId(), paymentDto.getBankCardName(),
                    paymentDto.getBankCardNumber(), paymentDto.getBankCardEndMonth(), paymentDto.getBankCardEndYear());
            payment.setBankCard(bankCard);
            bankCardService.createOrUpdate(bankCard);
            paymentService.createOrUpdate(payment);
            Receipt currReceipt = receiptService.findById(paymentDto.getReceiptId());
            currReceipt.setCurrAmount(paymentDto.getCurrAmount());
            currReceipt.setPayment(payment);
            receiptService.createOrUpdate(currReceipt);
        } catch (Exception e) {
            log.error("Failed to pay.", e);
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping()
    public void createPayment(@RequestBody PaymentDto paymentDto) {
        if (paymentDto != null) {
            paymentService.createOrUpdate(paymentDto.toPayment());
        }
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
