package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Tariff;
import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffService {
    private final TariffRepository tariffRepository;

    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Tariff findById(Long id) throws NotFoundException {
        return tariffRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Tariff createOrUpdate(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    public void delete(Long id) {
        tariffRepository.deleteById(id);
    }

    public List<Tariff> getActiveTariffs() {
        return tariffRepository.getActiveTariffs();
    }

    public Tariff getActiveTariffByService(String service) throws NotFoundException {
        List<Tariff> activeTariffs = getActiveTariffs();
        for (Tariff t : activeTariffs) {
            if (t.getService().getName().equalsIgnoreCase(service)) {
                return t;
            }
        }
        throw new NotFoundException();
    }
}
