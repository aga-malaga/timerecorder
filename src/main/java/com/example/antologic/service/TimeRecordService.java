package com.example.antologic.service;

import com.example.antologic.timeRecord.dto.TimeRecordDTO;
import com.example.antologic.timeRecord.dto.TimeRecordForm;

public interface TimeRecordService {

    TimeRecordDTO createRecord(TimeRecordForm form);
}
