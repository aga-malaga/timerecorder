package com.example.timerecorder.timeRecord.dto;

import com.example.timerecorder.timeRecord.domain.TimeRecord;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeRecordMapper {


    public static TimeRecord toTimeRecord(TimeRecordForm form) {
        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setStart(form.start());
        timeRecord.setStop(form.stop());
        return timeRecord;
    }

    public static TimeRecordDto toTimeDto(TimeRecord timeRecord){
        return new TimeRecordDto(timeRecord.getUuid(),timeRecord.getStart(),timeRecord.getStop(),
                timeRecord.getProjectUser().getProject().getUuid());
    }
}
