package com.example.antologic.project.report;

import com.example.antologic.project.report.TimePeriod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReportForm {
    @Enumerated(EnumType.STRING)
    private TimePeriod timePeriod;
    private UUID userUuid;
}
