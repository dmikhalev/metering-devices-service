package cs.vsu.meteringdevicesservice.controller;

import cs.vsu.meteringdevicesservice.dto.IdDto;
import cs.vsu.meteringdevicesservice.dto.UserDto;
import cs.vsu.meteringdevicesservice.entity.Role;
import cs.vsu.meteringdevicesservice.entity.User;
import cs.vsu.meteringdevicesservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/users")
public class AdminUserRestControllerV1 {

    private final UserService userService;

    @Autowired
    public AdminUserRestControllerV1(UserService userService) {
        this.userService = userService;
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
            User user = userDto.toUser();
            user.setRole(new Role("ROLE_USER"));
            userService.createOrUpdate(user);
        }
    }

    @DeleteMapping()
    public void deleteUser(@RequestBody IdDto id) {
        userService.delete(id.getId());
    }
}
