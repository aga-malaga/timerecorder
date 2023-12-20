package com.example.timerecorder.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectUpdateForm(
        @NotNull UUID projectUuid,
        @NotEmpty(message = "name is mandatory") String name,
        @NotEmpty String description,
        @NotNull(message = "start of the project is mandatory") LocalDateTime start,
        @NotNull(message = "end of the project is mandatory") LocalDateTime stop,
        @DecimalMin(value = "0", message = "budget must be greater than 0") BigDecimal budget
) {
}