package com.example.antologic.timeRecord.dto;

import com.example.antologic.timeRecord.TimeRecord;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeRecordMapper {


    public static TimeRecord toTimeRecord(TimeRecordForm form) {
        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setStart(form.getStart());
        timeRecord.setStop(form.getStop());
        return timeRecord;
    }

    public static TimeRecordDTO toTimeDTO(TimeRecord timeRecord){
        return new TimeRecordDTO(timeRecord.getUuid(),timeRecord.getStart(),timeRecord.getStop(),
                timeRecord.getProjectUser().getProject().getUuid());
    }
}
