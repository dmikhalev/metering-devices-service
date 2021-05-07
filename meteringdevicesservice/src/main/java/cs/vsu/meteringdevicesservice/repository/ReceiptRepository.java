package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT r FROM Receipt r WHERE r.apartment.user.id = :userId AND r.payment IS NULL")
    List<Receipt> findUnpaidReceiptsByUserId(Long userId);

    @Query("SELECT r FROM Receipt r WHERE r.apartment.user.id = :userId AND r.payment IS NOT NULL")
    List<Receipt> findPaidReceiptsByUserId(Long userId);

    @Query("SELECT r FROM Receipt r WHERE r.payment IS NOT NULL AND r.personalCode = :personalCode AND r.receiptData.tariff.service.name = :service " +
            "AND r.date IN (SELECT MAX(re.date) FROM Receipt re GROUP BY re.personalCode)")
    Optional<Receipt> findLastPaidReceiptBy(String service, Long personalCode);

    @Query("SELECT r FROM Receipt r WHERE r.payment IS NULL AND r.personalCode = :personalCode AND r.receiptData.tariff.service.name = :service " +
            "AND r.date IN (SELECT MAX(re.date) FROM Receipt re GROUP BY re.personalCode)")
    Optional<Receipt> findLastUnpaidReceiptBy(String service, Long personalCode);

    @Query("SELECT r.personalCode FROM Receipt r " +
            "WHERE r.apartment.id=:apartmentId AND r.receiptData.id=:receiptDataId " +
            "AND r.date IN (SELECT MAX(re.date) FROM Receipt re GROUP BY re.receiptData, re.apartment)")
    Optional<Long> findPersonalCodeByReceiptDataAndApartment(Long receiptDataId, Long apartmentId);
}
