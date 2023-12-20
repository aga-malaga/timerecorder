package com.example.timerecorder.project.dto;

import java.math.BigDecimal;

public record ProjectReportDto(String name,
                               Long time,
                               BigDecimal cost) {
}
