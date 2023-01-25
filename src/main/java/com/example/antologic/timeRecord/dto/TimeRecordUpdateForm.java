package com.example.antologic.timeRecord.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TimeRecordUpdateForm {
    @NotNull(message = "record field is mandatory")
    private UUID recordUuid;
    @NotNull
    private UUID userUuid;
    @NotNull(message = "project field is mandatory")
    private UUID projectUuid;
    @NotNull(message = "start field is mandatory")
    private LocalDateTime start;
    @NotNull(message = "end field is mandatory")
    private LocalDateTime stop;
}
