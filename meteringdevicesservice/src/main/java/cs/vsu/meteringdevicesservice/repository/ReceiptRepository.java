package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT r FROM Receipt r WHERE r.apartment.user.id = :userId AND r.payment IS NULL")
    List<Receipt> findUnpaidReceiptsByUserId(Long userId);

    @Query("SELECT r FROM Receipt r WHERE r.apartment.user.id = :userId AND r.payment IS NOT NULL")
    List<Receipt> findPaidReceiptsByUserId(Long userId);

    @Query("SELECT r FROM Receipt r WHERE r.apartment.user.id = :userId AND r.payment IS NOT NULL AND r.payment.date >= :date")
    List<Receipt> findPaidReceiptsAfterPaymentDate(Long userId, Date date);

    @Query(nativeQuery = true,
            value = "SELECT * FROM Receipt r JOIN receipt_data rd on rd.id = r.receipt_data_id JOIN tariff t on t.id = rd.tariff_id" +
                    " JOIN service s on s.id = t.service_id WHERE r.payment_id IS NOT NULL AND r.personal_code = :personalCode AND s.name = :service " +
                    "ORDER BY r.date DESC LIMIT 1")
    Optional<Receipt> findLastPaidReceiptBy(String service, Long personalCode);

    @Query(nativeQuery = true,
            value = "SELECT * FROM Receipt r JOIN receipt_data rd on rd.id = r.receipt_data_id JOIN tariff t on t.id = rd.tariff_id" +
                    " JOIN service s on s.id = t.service_id WHERE r.payment_id IS NULL AND r.personal_code = :personalCode AND s.name = :service " +
                    "ORDER BY r.date DESC LIMIT 1")
    Optional<Receipt> findLastUnpaidReceiptBy(String service, Long personalCode);

}
