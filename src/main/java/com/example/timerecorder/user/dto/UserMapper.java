package com.example.timerecorder.user.dto;


import com.example.timerecorder.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class UserMapper {

    public static User toEntity(UserCreateForm userCreateForm) {
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

    public static User update(User user, UserUpdateForm updateForm) {
        user.setLogin(updateForm.getLogin());
        user.setName(updateForm.getName());
        user.setSurname(updateForm.getSurname());
        user.setRole(updateForm.getRole());
        user.setEmail(updateForm.getEmail());
        user.setPassword(updateForm.getPassword());
        user.setCostPerHour(updateForm.getCostPerHour());
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
