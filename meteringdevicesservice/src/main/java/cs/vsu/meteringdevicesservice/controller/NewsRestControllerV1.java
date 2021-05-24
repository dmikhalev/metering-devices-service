package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.NewsDto;
import cs.vsu.meteringdevicesservice.entity.News;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/info/news")
public class NewsRestControllerV1 {
    private final NewsService newsService;

    @Autowired
    public NewsRestControllerV1(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping()
    public ResponseEntity<NewsDto> getNewsById(@RequestBody IdDto id) {
        News news;
        try {
            news = newsService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("News not found", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        NewsDto result = NewsDto.fromNews(news);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{days_count}")
    public ResponseEntity<List<NewsDto>> getNewsForTheLastDays(@PathVariable Integer days_count) {
        List<News> news = newsService.getNewsForTheLastDays(days_count);
        List<NewsDto> result = news.stream().map(NewsDto::fromNews).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createOrUpdateNews(@RequestBody NewsDto newsDto) {
        if (newsDto != null) {
            newsService.createOrUpdate(newsDto.toNews());
        }
    }

    @DeleteMapping()
    public void deleteNews(@RequestBody IdDto id) {
        newsService.delete(id.getId());
    }
}
