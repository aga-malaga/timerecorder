package com.example.antologic.timeRecord.dto;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
public record TimeRecordDTO(UUID userUuid,
                     UUID projectUuid,
                     LocalDateTime start,
                     LocalDateTime stop) {
}
