package com.example.antologic.user.dto;

import com.example.antologic.user.Role;

import java.math.BigDecimal;
import java.util.UUID;

public record UserDTO(
        UUID uuid,
        String login,
        String name,
        String surname,
        Role role,
        String email,
        String password,
        BigDecimal costPerHour)
{
}

