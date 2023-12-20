package com.example.timerecorder.timeRecord.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TimeRecordDto(UUID recordUuid,
                            LocalDateTime start,
                            LocalDateTime stop,
                            UUID projectUuid) {
}
