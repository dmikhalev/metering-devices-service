package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findAllByPostcode(Long postcode);
}
