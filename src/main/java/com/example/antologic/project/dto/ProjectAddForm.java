package com.example.antologic.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
