package com.example.timerecorder.project.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectSearchCriteria(String name,
                                    LocalDateTime start,
                                    LocalDateTime stop,
                                    List<UUID> userUuid,
                                    Boolean budget) {
}
