package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Executor;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ExecutorRepository;
import org.springframework.stereotype.Service;

@Service
public class ExecutorService {
    private final ExecutorRepository executorRepository;

    public ExecutorService(ExecutorRepository executorRepository) {
        this.executorRepository = executorRepository;
    }

    public Executor findById(Long id) {
        return executorRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Executor createOrUpdate(Executor executor) {
        return executorRepository.save(executor);
    }

    public void delete(Long id) {
        executorRepository.deleteById(id);
    }
}
