package com.example.timerecorder.timeRecord;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.timeRecord.dto.TimeRecordDto;
import com.example.timerecorder.timeRecord.dto.TimeRecordForm;
import com.example.timerecorder.timeRecord.dto.TimeRecordUpdateForm;

import java.util.UUID;

public interface TimeRecordFacade {
    TimeRecordDto createRecord(TimeRecordForm form);
    PageDTO findRecords(UUID userUuid, int pageNo, int pageSize, String sortBy);
    void deleteRecord(UUID userUuid, UUID recordUuid);
    void editRecord(TimeRecordUpdateForm recordForm);
}