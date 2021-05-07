package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.entity.Building;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByLogin(username).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createOrUpdate(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public String buildUserAddress(User user) {
        Apartment apartment = user.getApartment();
        Building building = apartment.getBuilding();
        return "г." + building.getCity() +
                ", ул." + building.getStreet() +
                ", д." + building.getNumber() +
                ", кв." + apartment.getNumber();
    }
}
