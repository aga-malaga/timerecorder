package com.example.timerecorder.project.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProjectRemoveForm(
        @NotNull UUID projectUuid,
        @NotNull UUID userUuid
) {
}