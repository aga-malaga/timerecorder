package com.example.antologic.project.report;

import java.math.BigDecimal;
import java.util.List;

public record ReportDTO(BigDecimal totalCost,
                        List projects) {

}
