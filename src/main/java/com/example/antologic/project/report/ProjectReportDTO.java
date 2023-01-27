package com.example.antologic.project.report;

import java.math.BigDecimal;

public record ProjectReportDTO(String name,
                               Long time,
                               BigDecimal cost) {
}
