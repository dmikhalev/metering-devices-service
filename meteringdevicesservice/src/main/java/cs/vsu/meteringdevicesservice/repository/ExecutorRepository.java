package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.Executor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutorRepository extends JpaRepository<Executor, Long> {
}
