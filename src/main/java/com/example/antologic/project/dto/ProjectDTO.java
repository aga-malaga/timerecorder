package com.example.antologic.project.dto;

import com.example.antologic.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record ProjectDTO(UUID uuid,
                         String name,
                         String description,
                         LocalDateTime start,
                         LocalDateTime stop,
                         BigDecimal budget,
                         Set<User> users) {
}
