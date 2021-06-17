package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.ReceiptData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptDataRepository extends JpaRepository<ReceiptData, Long> {

    List<ReceiptData> findAllByBuilding_Id(Long buildingId);
}
