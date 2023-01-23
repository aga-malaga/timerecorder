package com.example.antologic.timeRecord.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TimeRecordDTO(UUID recordUuid,
                            LocalDateTime start,
                            LocalDateTime stop,
                            UUID projectUuid) {
}
