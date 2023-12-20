package com.example.timerecorder.timeRecord.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record TimeRecordUpdateForm(
        @NotNull(message = "record field is mandatory") UUID recordUuid,
        @NotNull UUID userUuid,
        @NotNull(message = "project field is mandatory") UUID projectUuid,
        @NotNull(message = "start field is mandatory") LocalDateTime start,
        @NotNull(message = "end field is mandatory") LocalDateTime stop
) {
}