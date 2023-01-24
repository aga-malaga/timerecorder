package com.example.antologic.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectShiftForm {
    @NotNull
    private UUID projectUuid;
    @NotNull
    private UUID userUuid;
}
