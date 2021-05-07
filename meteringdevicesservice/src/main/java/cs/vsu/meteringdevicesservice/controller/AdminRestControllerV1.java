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

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminRestControllerV1 {

    private final UserService userService;

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserDto> getAdminById(@RequestBody IdDto id) {
        User user = userService.findById(id.getId());
        if (user == null) {
            log.error("Admin not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public void createAdmin(@RequestBody UserDto userDto) {
        if (userDto != null) {
            User admin = userDto.toUser();
            admin.setRole(new Role("ROLE_ADMIN"));
            userService.createOrUpdate(admin);
        }
    }

    @PutMapping()
    public void editAdmin(@RequestBody UserDto userDto) {
        if (userDto != null) {
            userService.createOrUpdate(userDto.toUser());
        }
    }

    @DeleteMapping()
    public void deleteAdmin(@RequestBody IdDto id) {
        userService.delete(id.getId());
    }

}
