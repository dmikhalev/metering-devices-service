package cs.vsu.meteringdevicesservice.component;

import cs.vsu.meteringdevicesservice.entity.Role;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.repository.RoleRepository;
import cs.vsu.meteringdevicesservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CommandLineAppStartupRunner(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        User admin = userRepository.findByLogin("admin").orElse(null);
        if (admin == null) {
            Role role = roleRepository.findByName("ROLE_ADMIN").orElse(null);
            if (role != null) {
                admin = new User();
                admin.setLogin("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(role);
                admin.setName("admin");
                userRepository.save(admin);
            }
        }
    }
}