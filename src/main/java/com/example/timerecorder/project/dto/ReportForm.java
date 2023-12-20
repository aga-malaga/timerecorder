package com.example.timerecorder.project.dto;

import com.example.timerecorder.project.domain.TimePeriod;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReportForm(
        @NotNull @Enumerated TimePeriod timePeriod,
        @NotNull UUID userUuid
) {
}