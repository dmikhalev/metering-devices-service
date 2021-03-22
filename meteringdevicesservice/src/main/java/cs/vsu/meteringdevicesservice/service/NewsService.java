package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.News;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.NewsRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public News createOrUpdate(News news) {
        return newsRepository.save(news);
    }

    public void delete(Long id) {
        newsRepository.deleteById(id);
    }
}
