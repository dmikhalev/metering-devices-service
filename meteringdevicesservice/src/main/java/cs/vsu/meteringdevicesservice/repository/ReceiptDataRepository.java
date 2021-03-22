package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.ReceiptData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptDataRepository extends JpaRepository<ReceiptData, Long> {
}
