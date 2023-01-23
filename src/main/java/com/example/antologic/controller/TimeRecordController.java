package com.example.antologic.controller;

import com.example.antologic.common.dto.PageDTO;
import com.example.antologic.project.dto.ProjectShiftForm;
import com.example.antologic.service.TimeRecordService;
import com.example.antologic.timeRecord.dto.TimeRecordDTO;
import com.example.antologic.timeRecord.dto.TimeRecordForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/records")
class TimeRecordController {

    private final TimeRecordService timeRecordService;

    @GetMapping
    public PageDTO getProjectsPaged(@RequestParam UUID userUuid,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort) {
        return timeRecordService.findRecords(userUuid, page, size, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeRecordDTO createRecord(@RequestBody @Valid TimeRecordForm recordForm) {
        return timeRecordService.createRecord(recordForm);
    }

    @DeleteMapping
    public void removeUserFromProject(@RequestParam UUID userUuid,
                                      @RequestParam UUID recordUuid) {
        timeRecordService.deleteRecord(userUuid, recordUuid);
    }

}
