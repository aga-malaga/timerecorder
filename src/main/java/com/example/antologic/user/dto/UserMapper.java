package com.example.antologic.user.dto;


import com.example.antologic.user.User;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public
class UserMapper {
    public static User toUser(UserCreateForm userCreateForm) {
        User user = new User();
        user.setLogin(userCreateForm.getLogin());
        user.setName(userCreateForm.getName());
        user.setSurname(userCreateForm.getSurname());
        user.setRole(userCreateForm.getRole());
        user.setEmail(userCreateForm.getEmail());
        user.setPassword(userCreateForm.getPassword());
        user.setCostPerHour(userCreateForm.getCostPerHour());

        return user;
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getUuid(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getEmail(),
                user.getCostPerHour(),
                user.getProjects().stream().map(p -> p.getName()).collect(Collectors.toSet()
                ));
    }
}
