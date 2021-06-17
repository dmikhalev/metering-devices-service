package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    Optional<Building> findByCityAndStreetAndNumber(String city, String street, String number);
}
