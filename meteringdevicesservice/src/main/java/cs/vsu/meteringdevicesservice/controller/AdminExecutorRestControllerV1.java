package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.ExecutorDto;
import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.entity.Executor;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.service.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/executor")
public class AdminExecutorRestControllerV1 {

    private final ExecutorService executorService;

    @Autowired
    public AdminExecutorRestControllerV1(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @GetMapping()
    public ResponseEntity<ExecutorDto> getExecutorById(@RequestBody IdDto id) {
        Executor executor;
        try {
            executor = executorService.findById(id.getId());
        } catch (NotFoundException e) {
            log.error("Executor not found.", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ExecutorDto result = ExecutorDto.fromExecutor(executor);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ExecutorDto>> getAllExecutors() {
        List<Executor> executors = executorService.findAll();
        List<ExecutorDto> result = executors.stream()
                .map(ExecutorDto::fromExecutor)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createOrUpdateExecutor(@RequestBody ExecutorDto executorDto) {
        if (executorDto != null) {
            executorService.createOrUpdate(executorDto.toExecutor());
        }
    }

    @DeleteMapping()
    public void deleteExecutor(@RequestBody IdDto id) {
        executorService.delete(id.getId());
    }
}
