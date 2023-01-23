package com.example.antologic.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAddForm {
    @NotNull
    private UUID projectUuid;
    @NotNull
    private UUID userUuid;
    @NotNull
    private LocalDateTime enterOn;
    @NotNull
    private LocalDateTime leaveOn;
}
