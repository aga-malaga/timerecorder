package com.example.antologic;

import com.example.antologic.user.Role;
import com.example.antologic.user.User;
import com.example.antologic.user.dto.UserCreateForm;
import com.example.antologic.user.dto.UserUpdateForm;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

public class UserTestFactory {

    public static User createUserAdmin(){
        return new User(1L,
                "login",
                "name",
                "surname",
                Role.ADMIN,
                "email@gmail.com",
                "password",
                BigDecimal.ONE);
    }

    public static User createUserEmployee(){
        return new User(2L,
                "loginEmployee",
                "nameE",
                "surnameE",
                Role.EMPLOYEE,
                "email@wp.pl",
                "password",
                BigDecimal.ONE);
    }

    public static UserCreateForm createUserForm(){
        return new UserCreateForm(
                "login2",
                "name",
                "surname",
                Role.EMPLOYEE,
                "emai222l@wp.pl",
                "password",
                BigDecimal.ONE);
    }

    public static UserUpdateForm createUpdateUserForm(){
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
