package com.example.timerecorder.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectForm {

    @NotEmpty(message = "name is mandatory")
    private String name;
    private String description = "";
    @NotNull(message = "start of the project is mandatory")
    private LocalDate startDate;
    @NotNull(message = "end of the project is mandatory")
    private LocalDate endDate;
    @DecimalMin(value = "0", message = "budget must be greater than 0")
    private BigDecimal budget;

}
