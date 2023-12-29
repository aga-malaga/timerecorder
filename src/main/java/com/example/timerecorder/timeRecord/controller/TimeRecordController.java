package com.example.timerecorder.timeRecord.controller;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.timeRecord.TimeRecordFacade;
import com.example.timerecorder.timeRecord.dto.TimeRecordDto;
import com.example.timerecorder.timeRecord.dto.TimeRecordForm;
import com.example.timerecorder.timeRecord.dto.TimeRecordUpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/records")
class TimeRecordController {

    private final TimeRecordFacade timeRecordService;

    @GetMapping
    public PageDTO getProjectsPaged(@RequestParam UUID userUuid,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort) {
        return timeRecordService.findRecords(userUuid, page, size, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeRecordDto createRecord(@RequestBody @Valid TimeRecordForm recordForm) {
        return timeRecordService.createRecord(recordForm);
    }

    @DeleteMapping
    public void removeRecord(@RequestParam UUID userUuid,
                             @RequestParam UUID recordUuid) {
        timeRecordService.deleteRecord(userUuid, recordUuid);
    }

    @PutMapping
    public void editRecord(@RequestBody @Valid TimeRecordUpdateForm recordForm) {
        timeRecordService.editRecord(recordForm);
    }
}