package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.News;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    private static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News findById(Long id) throws NotFoundException {
        return newsRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public News createOrUpdate(News news) {
        return newsRepository.save(news);
    }

    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    public List<News> getNewsForTheLastDays(Integer days) {
        Date date = new Date(System.currentTimeMillis() - (days * MILLIS_IN_DAY));
        return new ArrayList<>(newsRepository.findNewsByDateAfter(date));
    }
}
