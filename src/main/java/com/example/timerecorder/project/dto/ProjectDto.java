package com.example.timerecorder.project.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProjectDto(UUID uuid,
                         String name,
                         String description,
                         LocalDate start,
                         LocalDate stop,
                         BigDecimal budget,
                         List<ProjectUserDTO> userDTO) {
}
