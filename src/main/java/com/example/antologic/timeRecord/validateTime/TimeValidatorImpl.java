package com.example.antologic.timeRecord.validateTime;

import com.example.antologic.timeRecord.dto.TimeRecordForm;

import java.time.LocalDateTime;

class TimeValidatorImpl implements TimeValidator{

    @Override
    public boolean validateTime(final TimeRecordForm form,
                                final LocalDateTime start,
                                final LocalDateTime stop) {
        return form.getStart().isAfter(start) && form.getStop().isBefore(stop);
    }
}
