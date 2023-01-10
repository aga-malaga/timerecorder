package com.example.antologic.user;


import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserDTOExtended;
import com.example.antologic.user.dto.UserForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class Mapper {
    public static User mapFormToUser(UserForm userForm) {
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

    public static UserDTO mapUserToDto(User user) {
        return new UserDTO(
                user.getUuid(),
                user.getLogin()
        );
    }

    public static UserDTOExtended mapUserToDtoExtended(User user) {
        return new UserDTOExtended(
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
