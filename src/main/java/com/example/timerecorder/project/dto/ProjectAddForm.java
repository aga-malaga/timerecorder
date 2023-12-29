package com.example.timerecorder.project.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ProjectAddForm(
        @NotNull UUID projectUuid,
        @NotNull UUID userUuid,
        @NotNull LocalDate enterOn,
        @NotNull LocalDate leaveOn
) {
}