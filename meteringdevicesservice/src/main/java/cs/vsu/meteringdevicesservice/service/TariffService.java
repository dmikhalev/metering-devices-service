package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Tariff;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.TariffRepository;
import org.springframework.stereotype.Service;

@Service
public class TariffService {
    private final TariffRepository tariffRepository;

    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Tariff findById(Long id) {
        return tariffRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Tariff createOrUpdate(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    public void delete(Long id) {
        tariffRepository.deleteById(id);
    }
}
