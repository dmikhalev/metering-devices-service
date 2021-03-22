package cs.vsu.meteringdevicesservice.repository;

import cs.vsu.meteringdevicesservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
