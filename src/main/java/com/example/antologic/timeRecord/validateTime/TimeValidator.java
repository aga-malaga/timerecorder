package com.example.antologic.timeRecord.validateTime;

import com.example.antologic.timeRecord.dto.TimeRecordForm;

import java.time.LocalDateTime;

public interface TimeValidator {

    boolean validateTime(TimeRecordForm form, LocalDateTime start, LocalDateTime stop);
}
