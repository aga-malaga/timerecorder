package com.example.timerecorder.project.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReportDto(String name,
                        String surname,
                        BigDecimal totalCost,
                        List projects) {

}
