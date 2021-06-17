package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Payment;
import cs.vsu.meteringdevicesservice.entity.Receipt;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ReceiptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReceiptService {
    private static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

    private final ReceiptRepository receiptRepository;
    private final PaymentService paymentService;

    public ReceiptService(ReceiptRepository receiptRepository, PaymentService paymentService) {
        this.receiptRepository = receiptRepository;
        this.paymentService = paymentService;
    }

    public Receipt findById(Long id) throws NotFoundException {
        return receiptRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Receipt createOrUpdate(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public void delete(Long id) {
        receiptRepository.deleteById(id);
    }

    public List<Receipt> findUnpaidReceiptsByUserId(Long userId) {
        return new ArrayList<>(receiptRepository.findUnpaidReceiptsByUserId(userId));
    }

    public List<Receipt> findPaidReceiptsByUserId(Long userId) {
        return new ArrayList<>(receiptRepository.findPaidReceiptsByUserId(userId));
    }

    public List<Receipt> findPaidReceiptsFotTheLastDays(long userId, int days) {
        Date date = new Date(System.currentTimeMillis() - (days * MILLIS_IN_DAY));
        return new ArrayList<>(receiptRepository.findPaidReceiptsAfterPaymentDate(userId, date));
    }

    public Receipt findLastPaidReceiptBy(String serviceName, Long personalCode) {
        return receiptRepository.findLastPaidReceiptBy(serviceName, personalCode).orElse(null);
    }

    public Receipt findLastUnpaidReceiptBy(String serviceName, Long personalCode) {
        return receiptRepository.findLastUnpaidReceiptBy(serviceName, personalCode).orElse(null);
    }

    @Transactional
    public boolean payUnpaidReceipt(String serviceName, long personalCode, double sum, long amount) {
        Receipt unpaidReceipt = findLastUnpaidReceiptBy(serviceName, personalCode);
        if (unpaidReceipt != null) {
            Payment payment = paymentService.createOrUpdate(new Payment(sum));
            unpaidReceipt.setCurrAmount(amount);
            unpaidReceipt.setPayment(payment);
            createOrUpdate(unpaidReceipt);
            return true;
        }
        return false;
    }
}
