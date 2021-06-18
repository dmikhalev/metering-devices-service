package cs.vsu.meteringdevicesservice.service;

import cs.vsu.meteringdevicesservice.entity.Apartment;
import cs.vsu.meteringdevicesservice.entity.Building;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.repository.UserRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
public class UserService {
    private static final String DEFAULT_PASSWORD = "NewPass2022";
    private static final int GENERATED_PASSWORD_LENGTH = 8;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (apartment == null) {
            return "";
        }
        Building building = apartment.getBuilding();
        return "г." + building.getCity() +
                ", ул." + building.getStreet() +
                ", д." + building.getNumber() +
                ", кв." + apartment.getNumber();
    }

    public void resetPassword(User user) {
        if (user != null) {
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            userRepository.save(user);
        }
    }

    public String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
        return generator.generate(GENERATED_PASSWORD_LENGTH);
    }
}
