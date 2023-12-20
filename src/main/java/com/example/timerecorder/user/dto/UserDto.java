package com.example.timerecorder.user.dto;

import com.example.timerecorder.common.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID uuid,
        String login,
        String name,
        String surname,
        Role role,
        String email,
        BigDecimal costPerHour,
        List<String> projectName
) {
}

