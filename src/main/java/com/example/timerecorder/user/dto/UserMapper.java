package com.example.timerecorder.user.dto;


import com.example.timerecorder.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class UserMapper {

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

    public static UserDto toDto(User user) {

        return new UserDto(
                user.getUuid(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getEmail(),
                user.getCostPerHour(),
                user.getProjects().stream()
                        .map(pu -> pu.getProject().getName())
                        .collect(Collectors.toList())
        );
    }
}
