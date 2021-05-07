package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.ReceiptData;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ReceiptDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptDataService {
    private final ReceiptDataRepository receiptDataRepository;

    public ReceiptDataService(ReceiptDataRepository receiptDataRepository) {
        this.receiptDataRepository = receiptDataRepository;
    }

    public ReceiptData findById(Long id) throws NotFoundException {
        return receiptDataRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<ReceiptData> findAllByBuildingId(Long buildingId){
        return new ArrayList<>(receiptDataRepository.findAllByBuilding_Id(buildingId));
    }

    public ReceiptData createOrUpdate(ReceiptData receiptData) {
        return receiptDataRepository.save(receiptData);
    }

    public void delete(Long id) {
        receiptDataRepository.deleteById(id);
    }
}
