package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findNewsByDateAfter(Date date);
}
