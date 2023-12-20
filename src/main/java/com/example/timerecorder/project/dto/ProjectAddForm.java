package com.example.timerecorder.project.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectAddForm(
        @NotNull UUID projectUuid,
        @NotNull UUID userUuid,
        @NotNull LocalDateTime enterOn,
        @NotNull LocalDateTime leaveOn
) {
}