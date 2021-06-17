package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cs.vsu.meteringdevicesservice.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String password;

    public User toUser(BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.setId(id);
        user.setLogin(username);
        user.setName(name);
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getLogin());
        userDto.setName(user.getName());
        return userDto;
    }
}
