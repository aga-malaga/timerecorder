package com.example.antologic.user;


import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public
class UserMapper {
    public static User toUser(UserForm userForm) {
        return new User(
                userForm.getLogin(),
                userForm.getName(),
                userForm.getSurname(),
                userForm.getRole(),
                userForm.getEmail(),
                userForm.getPassword(),
                userForm.getCostPerHour()
        );
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getUuid(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getEmail(),
                user.getPassword(),
                user.getCostPerHour()
        );
    }
}
