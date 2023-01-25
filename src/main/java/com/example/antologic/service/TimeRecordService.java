package com.example.antologic.service;

import com.example.antologic.common.dto.PageDTO;
import com.example.antologic.timeRecord.dto.TimeRecordDTO;
import com.example.antologic.timeRecord.dto.TimeRecordForm;
import com.example.antologic.timeRecord.dto.TimeRecordUpdateForm;

import java.util.UUID;

public interface TimeRecordService {

    TimeRecordDTO createRecord(TimeRecordForm form);

    PageDTO findRecords(UUID userUuid, int pageNo, int pageSize, String sortBy);

    void deleteRecord(UUID userUuid, UUID recordUuid);

    void editRecord(TimeRecordUpdateForm recordForm);
}
