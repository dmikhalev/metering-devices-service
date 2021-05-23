package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.exception.NotFoundException;
import cs.vsu.meteringdevicesservice.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceService {
    public static enum ServiceName {
        GAS("Газ"),
        WATER("Вода"),
        ELECTRICITY("Электричество");

        private final String viewName;

        ServiceName(String viewName) {
            this.viewName = viewName;
        }

        public String getViewName() {
            return viewName;
        }
    }

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public cs.vsu.meteringdevicesservice.entity.Service findByName(String name) throws NotFoundException {
        return serviceRepository.findByName(name).orElseThrow(NotFoundException::new);
    }

    public cs.vsu.meteringdevicesservice.entity.Service createOrUpdate(cs.vsu.meteringdevicesservice.entity.Service service) {
        return serviceRepository.save(service);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

    public static String getServiceName(String service) {
        Map<String, String> map = new HashMap<>();
        map.put(ServiceName.GAS.name().toLowerCase(), ServiceName.GAS.viewName);
        map.put(ServiceName.WATER.name().toLowerCase(), ServiceName.WATER.viewName);
        map.put(ServiceName.ELECTRICITY.name().toLowerCase(), ServiceName.ELECTRICITY.viewName);
        return map.get(service);
    }
}
