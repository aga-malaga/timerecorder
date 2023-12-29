package com.example.timerecorder.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProjectUpdateForm(
        @NotNull UUID projectUuid,
        @NotEmpty(message = "name is mandatory") String name,
        @NotEmpty String description,
        @NotNull(message = "start of the project is mandatory") LocalDate start,
        @NotNull(message = "end of the project is mandatory") LocalDate stop,
        @DecimalMin(value = "0", message = "budget must be greater than 0") BigDecimal budget
) {
}