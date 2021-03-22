package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.ReceiptData;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ReceiptDataRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiptDataService {
    private final ReceiptDataRepository receiptDataRepository;

    public ReceiptDataService(ReceiptDataRepository receiptDataRepository) {
        this.receiptDataRepository = receiptDataRepository;
    }

    public ReceiptData findById(Long id) {
        return receiptDataRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public ReceiptData createOrUpdate(ReceiptData receiptData) {
        return receiptDataRepository.save(receiptData);
    }

    public void delete(Long id) {
        receiptDataRepository.deleteById(id);
    }
}
