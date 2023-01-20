package com.example.antologic.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdateForm {
    @NotNull
    private UUID projectUuid;
    @NotEmpty(message = "name is mandatory")
    private String name;
    private String description = "";
    @NotNull(message = "start of the project is mandatory")
    private LocalDateTime start;
    @NotNull(message = "end of the project is mandatory")
    private LocalDateTime stop;
    @DecimalMin(value = "0", message = "budget must be greater than 0")
    private BigDecimal budget;
}
