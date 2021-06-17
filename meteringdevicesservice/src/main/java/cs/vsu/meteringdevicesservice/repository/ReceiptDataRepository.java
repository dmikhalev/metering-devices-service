package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.ReceiptData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiptDataRepository extends JpaRepository<ReceiptData, Long> {

    List<ReceiptData> findAllByBuilding_Id(Long buildingId);

    Optional<ReceiptData> findByBuilding_IdAndTariff_IdAndExecutor_Id(Long buildingId, Long tariffId, Long executorId);

    List<ReceiptData> findAll();
}
