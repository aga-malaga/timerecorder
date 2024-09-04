package com.example.timerecorder.user.dto;

public record LoginRequest(
        String login,
        String password
) {
}