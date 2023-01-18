package com.example.antologic.project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectForm {

    @NotEmpty(message = "name is mandatory")
    private String name;
    private String description;
    @NotEmpty(message = "start of the project is mandatory")
    private LocalDateTime start;
    @NotEmpty(message = "end of the project is mandatory")
    private LocalDateTime stop;
    @NotNull(message = "budget is mandatory")
    private BigDecimal budget;

}
