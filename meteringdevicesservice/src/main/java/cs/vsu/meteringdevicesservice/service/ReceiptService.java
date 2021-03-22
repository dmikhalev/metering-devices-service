package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Receipt;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public Receipt findById(Long id) {
        return receiptRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Receipt createOrUpdate(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public void delete(Long id) {
        receiptRepository.deleteById(id);
    }
}
