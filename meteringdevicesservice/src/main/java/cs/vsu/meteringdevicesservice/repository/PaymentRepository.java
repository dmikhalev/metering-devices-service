package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT p FROM Payment p WHERE p.bankCard.user.id=:id ORDER BY p.date DESC LIMIT 1", nativeQuery = true)
    Optional<Payment> findLastByUserId(Long id);
}
