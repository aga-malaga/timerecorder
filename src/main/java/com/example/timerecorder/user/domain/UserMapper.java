package com.example.timerecorder.user.domain;


import com.example.timerecorder.user.dto.UserCreateForm;
import com.example.timerecorder.user.dto.UserDto;
import com.example.timerecorder.user.dto.UserSecurity;
import com.example.timerecorder.user.dto.UserUpdateForm;

public class UserMapper {

    static User toEntity(UserCreateForm userCreateForm, String password) {
        User user = new User();
        user.setLogin(userCreateForm.getLogin());
        user.setName(userCreateForm.getName());
        user.setSurname(userCreateForm.getSurname());
        user.setRole(userCreateForm.getRole());
        user.setEmail(userCreateForm.getEmail());
        user.setPassword(password);
        user.setCostPerHour(userCreateForm.getCostPerHour());
        return user;
    }

    static User update(User user, UserUpdateForm updateForm, String password) {
        user.setLogin(updateForm.getLogin());
        user.setName(updateForm.getName());
        user.setSurname(updateForm.getSurname());
        user.setRole(updateForm.getRole());
        user.setEmail(updateForm.getEmail());
        user.setPassword(password);
        user.setCostPerHour(updateForm.getCostPerHour());
        return user;
    }

    static UserDto toDto(User user) {
        return new UserDto(
                user.getUuid(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getRole(),
                user.getEmail(),
                user.getCostPerHour(),
                user.getProjects().stream()
                        .map(projectUser -> projectUser.getProject().getName())
                        .toList());
    }

    static UserSecurity toUserSecurity(User user) {
        return new UserSecurity(user.getUuid(), user.getLogin(), user.getRole().name());
    }
}