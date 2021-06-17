package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, Long> {

    Optional<Info> findByName(String name);
}

