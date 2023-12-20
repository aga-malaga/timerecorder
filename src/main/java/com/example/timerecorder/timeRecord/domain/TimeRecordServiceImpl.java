package com.example.timerecorder.timeRecord.domain;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.common.dto.PageMapper;
import com.example.timerecorder.common.exception.ConflictException;
import com.example.timerecorder.common.exception.NotFoundException;
import com.example.timerecorder.project.Project;
import com.example.timerecorder.project.domain.ProjectRepository;
import com.example.timerecorder.projectUser.domain.ProjectUser;
import com.example.timerecorder.projectUser.domain.ProjectUserRepository;
import com.example.timerecorder.timeRecord.TimeRecordFacade;
import com.example.timerecorder.timeRecord.dto.TimeRecordDto;
import com.example.timerecorder.timeRecord.dto.TimeRecordForm;
import com.example.timerecorder.timeRecord.dto.TimeRecordMapper;
import com.example.timerecorder.timeRecord.dto.TimeRecordUpdateForm;
import com.example.timerecorder.timeRecord.validateTime.TimeValidator;
import com.example.timerecorder.user.domain.User;
import com.example.timerecorder.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.timerecorder.timeRecord.dto.TimeRecordMapper.toTimeDto;
import static com.example.timerecorder.timeRecord.dto.TimeRecordMapper.toTimeRecord;

@Service
@RequiredArgsConstructor
class TimeRecordServiceImpl implements TimeRecordFacade {

    private final TimeRecordRepository timeRecordRepository;
    private final ProjectUserRepository projectUserRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TimeValidator timeValidator;

    @Transactional
    @Override
    public TimeRecordDto createRecord(TimeRecordForm form) {
        final Project project = findProject(form.projectUuid());
        final User user = findUser(form.userUuid(), "Employee not found");

        final ProjectUser projectAndUser = findByProjectAndUser(project, user);

        validateTime(form.start(), form.stop(), project, projectAndUser);
        verifyIfTimeRecordEmpty(form.start(), form.stop(), form.userUuid());

        TimeRecord timeRecord = toTimeRecord(form);
        timeRecord.setProjectUser(projectAndUser);
        timeRecord.setSalary(user.getCostPerHour());

        timeRecordRepository.save(timeRecord);

        return toTimeDto(timeRecord);
    }

    @Transactional
    @Override
    public PageDTO findRecords(UUID userUuid, int pageNo, int pageSize, String sortBy) {
        final User user = findUser(userUuid, "User not found");

        final ProjectUser projectUser = projectUserRepository.findProjectUserByUser(user).orElseThrow(() ->
                new NotFoundException("User not found in any project"));

        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<TimeRecordDto> timeRecords = timeRecordRepository.findAllByProjectUser(projectUser, p)
                .map(TimeRecordMapper::toTimeDto);
        return PageMapper.toDtoTR(timeRecords);
    }

    @Transactional
    @Override
    public void deleteRecord(UUID userUuid, UUID recordUuid) {
        findUser(userUuid, "User not found");

        timeRecordRepository.removeTimeRecordByUuid(recordUuid);
    }

    @Transactional
    @Override
    public void editRecord(TimeRecordUpdateForm form) {

        final TimeRecord record = timeRecordRepository.findTimeRecordByUuid(form.recordUuid());
        final Project project = findProject(form.projectUuid());
        final User user = findUser(form.userUuid(), "Employee not found");
        final ProjectUser projectAndUser = findByProjectAndUser(project, user);

        final LocalDateTime start = form.start();
        final LocalDateTime stop = form.stop();
        validateTime(start, stop, project, projectAndUser);
        verifyIfTimeRecordEmpty(start, stop, form.userUuid());

        record.setProjectUser(projectAndUser);
        record.setStart(start);
        record.setStop(stop);

        timeRecordRepository.save(record);
    }

    private Project findProject(final UUID form) {
        return projectRepository.findProjectByUuid(form)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }

    private User findUser(final UUID form, final String Employee_not_found) {
        return userRepository.findByUuid(form)
                .orElseThrow(() -> new NotFoundException(Employee_not_found));
    }

    private ProjectUser findByProjectAndUser(final Project project, final User user) {
        return projectUserRepository.findByProjectAndUser(project, user)
                .orElseThrow(() -> new NotFoundException("This employee is not in the project"));
    }

    private void validateTime(final LocalDateTime start, LocalDateTime stop, final Project project, final ProjectUser projectAndUser) {
        if (!timeValidator.validateTime(start, stop, projectAndUser, project)) {
            throw new ConflictException("Time conflict occurred - user is not yet or no longer in the project");
        }
    }

    private void verifyIfTimeRecordEmpty(final LocalDateTime start, LocalDateTime stop, UUID userUuid) {
        if (timeRecordRepository.existTimeRecordBetween(start, stop, userUuid)) {
            throw new ConflictException("This time period is already occupied");
        }
    }
}
