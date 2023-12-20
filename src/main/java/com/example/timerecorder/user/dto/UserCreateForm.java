package com.example.timerecorder.user.dto;

import com.example.timerecorder.common.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateForm {

    @NotEmpty(message = "login is mandatory")
    private String login;
    @NotEmpty(message = "name is mandatory")
    private String name;
    @NotEmpty(message = "surname is mandatory")
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Email
    @NotEmpty(message = "email is mandatory")
    private String email;
    @NotEmpty(message = "password is mandatory")
    private String password;
    @NotNull(message = "costPerHour is mandatory")
    private BigDecimal costPerHour;
}
