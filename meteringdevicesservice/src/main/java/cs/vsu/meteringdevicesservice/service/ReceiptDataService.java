package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.ReceiptData;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ReceiptDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceiptDataService {
    private final ReceiptDataRepository receiptDataRepository;

    public ReceiptDataService(ReceiptDataRepository receiptDataRepository) {
        this.receiptDataRepository = receiptDataRepository;
    }

    public ReceiptData findById(Long id) throws NotFoundException {
        return receiptDataRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<ReceiptData> findAllByBuildingId(Long buildingId) {
        return new ArrayList<>(receiptDataRepository.findAllByBuilding_Id(buildingId));
    }

    public ReceiptData createOrUpdate(ReceiptData receiptData) {
        return receiptDataRepository.save(receiptData);
    }

    public void delete(Long id) {
        receiptDataRepository.deleteById(id);
    }

    public ReceiptData findBy(Long buildingId, Long tariffId, Long executorId) throws NotFoundException {
        return receiptDataRepository.findByBuilding_IdAndTariff_IdAndExecutor_Id(buildingId, tariffId, executorId)
                .orElseThrow(NotFoundException::new);
    }

    public Map<Long, Map<ServiceService.ServiceName, ReceiptData>> getMappedReceiptDatas() {
        Map<Long, Map<ServiceService.ServiceName, ReceiptData>> result = new HashMap<>();
        List<ReceiptData> allReceiptDatas = receiptDataRepository.findAll();
        allReceiptDatas.forEach(rd -> {
            long buildingId = rd.getBuilding().getId();
            if (!result.containsKey(buildingId)) {
                result.put(buildingId, new HashMap<>());
            }
            ServiceService.ServiceName serviceName = ServiceService.ServiceName.findByName(rd.getTariff().getService().getName());
            result.get(buildingId).put(serviceName, rd);
        });
        return result;
    }
}
