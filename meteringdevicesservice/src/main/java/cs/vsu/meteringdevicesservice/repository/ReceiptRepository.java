package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

}
