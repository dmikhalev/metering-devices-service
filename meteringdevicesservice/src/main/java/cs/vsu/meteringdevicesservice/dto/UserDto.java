package cs.vsu.meteringdevicesservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cs.vsu.meteringdevicesservice.entity.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String phoneNumber;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(username);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getLogin());
        userDto.setName(user.getName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }
}
