package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ServiceRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public cs.vsu.meteringdevicesservice.entity.Service findByName(String name) {
        return serviceRepository.findByName(name).orElseThrow(NotFoundException::new);
    }

    public cs.vsu.meteringdevicesservice.entity.Service createOrUpdate(cs.vsu.meteringdevicesservice.entity.Service service) {
        return serviceRepository.save(service);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
