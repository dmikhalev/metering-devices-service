package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Receipt;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
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

    public Receipt findLastPaidReceiptBy(String serviceName, Long personalCode) {
        return receiptRepository.findLastPaidReceiptBy(serviceName, personalCode).orElseThrow(NotFoundException::new);
    }

    public Receipt findLastUnpaidReceiptBy(String serviceName, Long personalCode) {
        return receiptRepository.findLastUnpaidReceiptBy(serviceName, personalCode).orElseThrow(NotFoundException::new);
    }

    public Long findPersonalCodeByReceiptDataAndApartment(long receiptDataId, long apartmentId) {
        return receiptRepository.findPersonalCodeByReceiptDataAndApartment(receiptDataId, apartmentId).orElseThrow(NotFoundException::new);
    }
}
