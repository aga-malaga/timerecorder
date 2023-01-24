package com.example.antologic.project.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectDTOBudget(UUID uuid,
                               String name,
                               String description,
                               LocalDateTime start,
                               LocalDateTime stop,
                               BigDecimal budget,
                               BigDecimal budgetPercent,
                               List<ProjectUserDTO> userDTO) {
}
