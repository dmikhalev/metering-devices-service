package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.PasswordDto;
import cs.vsu.meteringdevicesservice.dto.UserDto;
import cs.vsu.meteringdevicesservice.entity.Role;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.service.RoleService;
import cs.vsu.meteringdevicesservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/users")
public class AdminUserRestControllerV1 {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserRestControllerV1(UserService userService, RoleService roleService,
                                     @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public ResponseEntity<UserDto> getUserById(@RequestBody IdDto id) {
        User user = userService.findById(id.getId());
        if (user == null) {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDto> result = users.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createOrUpdateUser(@RequestBody UserDto userDto) {
        if (userDto != null) {
            User user;
            if (userDto.getId() != null) {
                user = userService.findById(userDto.getId());
                user.setName(userDto.getName());
                user.setLogin(userDto.getUsername());
                if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                }
            } else {
                user = userDto.toUser(passwordEncoder);
                Role role = roleService.findByName("ROLE_USER");
                user.setRole(role);
            }
            userService.createOrUpdate(user);
        }
    }

    @DeleteMapping()
    public void deleteUser(@RequestBody IdDto id) {
        userService.delete(id.getId());
    }

    @PostMapping(value = "/reset_pass")
    public ResponseEntity<?> resetPassword(@RequestBody IdDto id) {
        User user = userService.findById(id.getId());
        if (user != null) {
            userService.resetPassword(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("User not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/generate_pass")
    public ResponseEntity<PasswordDto> generatePassword() {
        return new ResponseEntity<>(new PasswordDto(userService.generatePassword()), HttpStatus.OK);
    }
}
