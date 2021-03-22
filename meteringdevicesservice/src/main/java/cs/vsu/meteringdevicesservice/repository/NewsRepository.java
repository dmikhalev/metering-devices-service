package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
