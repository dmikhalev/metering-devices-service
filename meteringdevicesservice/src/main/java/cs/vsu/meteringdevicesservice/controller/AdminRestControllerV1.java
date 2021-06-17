package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.UserDto;
import cs.vsu.meteringdevicesservice.entity.Role;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminRestControllerV1(UserService userService, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public ResponseEntity<UserDto> getAdminById(@RequestBody IdDto id) {
        User user = userService.findById(id.getId());
        if (user == null || !user.getRole().getName().equalsIgnoreCase("ROLE_ADMIN")) {
            log.error("Admin not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createOrUpdateAdmin(@RequestBody UserDto userDto) {
        if (userDto != null) {
            User oldAdmin = userService.findByUsername(userDto.getUsername());
            if (oldAdmin != null && !oldAdmin.getRole().getName().equalsIgnoreCase("ROLE_ADMIN")) {
                log.error("Edited user is not admin");
                return;
            }
            User admin = userDto.toUser(passwordEncoder);
            admin.setRole(new Role("ROLE_ADMIN"));
            userService.createOrUpdate(admin);
        }
    }

    @DeleteMapping()
    public void deleteAdmin(@RequestBody IdDto id) {
        userService.delete(id.getId());
    }
}
