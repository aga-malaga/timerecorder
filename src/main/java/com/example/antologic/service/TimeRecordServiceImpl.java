package com.example.antologic.service;

import com.example.antologic.common.ConflictException;
import com.example.antologic.common.NotFoundException;
import com.example.antologic.common.dto.PageDTO;
import com.example.antologic.common.dto.PageMapper;
import com.example.antologic.project.Project;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.repository.ProjectRepository;
import com.example.antologic.repository.ProjectUserRepository;
import com.example.antologic.repository.TimeRecordRepository;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.timeRecord.TimeRecord;
import com.example.antologic.timeRecord.dto.TimeRecordDTO;
import com.example.antologic.timeRecord.dto.TimeRecordForm;
import com.example.antologic.timeRecord.dto.TimeRecordMapper;
import com.example.antologic.timeRecord.dto.TimeRecordUpdateForm;
import com.example.antologic.timeRecord.validateTime.TimeValidator;
import com.example.antologic.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
class TimeRecordServiceImpl implements TimeRecordService {

    private final TimeRecordRepository timeRecordRepository;

    private final ProjectUserRepository projectUserRepository;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final TimeValidator timeValidator;


    public TimeRecordDTO createRecord(TimeRecordForm form) {

        final Project project = projectRepository.findProjectByUuid(form.getProjectUuid())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        final User user = userRepository.findByUuid(form.getUserUuid())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        final ProjectUser projectAndUser = projectUserRepository.findByProjectAndUser(project, user)
                .orElseThrow(() -> new NotFoundException("This employee is not in the project"));

        if (!timeValidator.validateTime(form, projectAndUser, project)) {
            throw new ConflictException("Time conflict occurred - user is not yet or no longer in the project");
        }
        if (timeRecordRepository.existTimeRecordBetween(form.getStart(), form.getStop(), form.getUserUuid())) {
            throw new ConflictException("This time period is already occupied");
        }
        TimeRecord timeRecord = TimeRecordMapper.toTimeRecord(form);
        timeRecord.setProjectUser(projectAndUser);
        timeRecord.setSalary(user.getCostPerHour());

        timeRecordRepository.save(timeRecord);

        return TimeRecordMapper.toTimeDTO(timeRecord);
    }

    public PageDTO findRecords(UUID userUuid, int pageNo, int pageSize, String sortBy) {
        final User user = userRepository.findByUuid(userUuid).orElseThrow(() ->
                new NotFoundException("User not found"));

        final ProjectUser projectUser = projectUserRepository.findProjectUserByUser(user).orElseThrow(() ->
                new NotFoundException("User not found in any project"));

        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<TimeRecordDTO> timeRecords = timeRecordRepository.findAllByProjectUser(projectUser, p)
                .map(TimeRecordMapper::toTimeDTO);
        return PageMapper.toDtoTR(timeRecords);
    }

    @Transactional
    public void deleteRecord(UUID userUuid, UUID recordUuid) {
        userRepository.findByUuid(userUuid).orElseThrow(() ->
                new NotFoundException("User not found"));

        timeRecordRepository.removeTimeRecordByUuid(recordUuid);
    }

    @Transactional
    public void editRecord(TimeRecordUpdateForm form) {

        final TimeRecord record = timeRecordRepository.findTimeRecordByUuid(form.getRecordUuid());

        final Project project = projectRepository.findProjectByUuid(form.getProjectUuid())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        final User user = userRepository.findByUuid(form.getUserUuid())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        final ProjectUser projectAndUser = projectUserRepository.findByProjectAndUser(project, user)
                .orElseThrow(() -> new NotFoundException("This employee is not in the project"));

        if (!timeValidator.validateTime(form, projectAndUser, project)) {
            throw new ConflictException("Time conflict occurred - user is not yet or no longer in the project");
        }
        if (timeRecordRepository.existTimeRecordBetween(form.getStart(), form.getStop(), form.getUserUuid())) {
            throw new ConflictException("This time period is already occupied");
        }
        record.setProjectUser(projectAndUser);
        record.setStart(form.getStart());
        record.setStop(form.getStop());
    }
}
