package com.example.timerecorder.user.dto;

import java.util.UUID;

public record UserSecurity(
        UUID uuid,
        String login,
        String role
) {
}