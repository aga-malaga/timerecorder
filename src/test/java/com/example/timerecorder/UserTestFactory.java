package com.example.timerecorder;

import com.example.timerecorder.common.Role;
import com.example.timerecorder.user.domain.User;
import com.example.timerecorder.user.dto.UserCreateForm;
import com.example.timerecorder.user.dto.UserUpdateForm;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class UserTestFactory {

    public static User createUserAdmin() {
        User user = new User();
        user.setId(1L);
        user.setLogin("login");
        user.setName("name");
        user.setSurname("surname");
        user.setRole(Role.ADMIN);
        user.setEmail("email@gmail.com");
        user.setPassword("pass");
        user.setCostPerHour(BigDecimal.ONE);
        user.setProjects(List.of());
        return user;
    }

    public static User createUserEmployee() {
        User user = new User();
        user.setId(1L);
        user.setLogin("login");
        user.setName("name");
        user.setSurname("surname");
        user.setRole(Role.EMPLOYEE);
        user.setEmail("email@gmail.com");
        user.setPassword("pass");
        user.setCostPerHour(BigDecimal.ONE);
        user.setProjects(List.of());
        return user;
    }

    public static UserCreateForm createUserForm() {
        return new UserCreateForm(
                "login2",
                "name",
                "surname",
                Role.EMPLOYEE,
                "emai222l@wp.pl",
                "password",
                BigDecimal.ONE);
    }

    public static UserUpdateForm createUpdateUserForm() {
        return new UserUpdateForm(
                UUID.fromString("8fcb1ba3-bbeb-40b2-8f74-9fab5071d3f0"),
                "login2",
                "name",
                "surname",
                Role.EMPLOYEE,
                "emai222l@wp.pl",
                "password",
                BigDecimal.ONE);
    }
}
