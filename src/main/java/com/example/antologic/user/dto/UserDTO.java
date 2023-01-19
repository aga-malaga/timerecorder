package com.example.antologic.user.dto;

import com.example.antologic.project.Project;
import com.example.antologic.user.Role;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record UserDTO(
        UUID uuid,
        String login,
        String name,
        String surname,
        Role role,
        String email,
        BigDecimal costPerHour,
        Set<String> projects)
{
}

