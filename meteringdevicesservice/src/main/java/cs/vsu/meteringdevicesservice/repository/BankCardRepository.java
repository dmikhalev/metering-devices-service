package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {
    Optional<BankCard> findByUser_Id(Long userId);

    List<BankCard> findAllByUser_Id(Long userId);
}
