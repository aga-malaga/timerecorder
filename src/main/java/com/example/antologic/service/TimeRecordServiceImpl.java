package com.example.antologic.service;

import com.example.antologic.common.ConflictException;
import com.example.antologic.common.NotFoundException;
import com.example.antologic.project.Project;
import com.example.antologic.timeRecord.validateTime.TimeValidator;
import com.example.antologic.repository.ProjectRepository;
import com.example.antologic.repository.TimeRecordRepository;
import com.example.antologic.timeRecord.dto.TimeRecordDTO;
import com.example.antologic.timeRecord.dto.TimeRecordForm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class TimeRecordServiceImpl implements TimeRecordService {

    private final TimeRecordRepository timeRecordRepository;

    private final ProjectRepository projectRepository;

    private final TimeValidator timeValidator;

    public TimeRecordDTO createRecord(TimeRecordForm form) {

        final Project project = projectRepository.findProjectByUuidAndUsersUuid(form.getProjectUuid(), form.getUserUuid())
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        if (!timeValidator.validateTime(form, project.getStart(), project.getStop())) {
            throw new ConflictException("Time conflict occurred");
        }
        return new TimeRecordDTO();
    }
}
