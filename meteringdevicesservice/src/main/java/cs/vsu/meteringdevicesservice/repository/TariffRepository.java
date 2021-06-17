package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

    @Query("SELECT t FROM Tariff t WHERE t.date IN (SELECT MAX(tr.date) FROM Tariff tr GROUP BY tr.service)")
    List<Tariff> getActiveTariffs();

}
