package com.example.antologic.timeRecord.validateTime;

import com.example.antologic.project.Project;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.timeRecord.dto.TimeRecordForm;
import com.example.antologic.timeRecord.dto.TimeRecordUpdateForm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class TimeValidatorImpl implements TimeValidator {

    @Override
    public boolean validateTime(final TimeRecordForm form, final ProjectUser projectUser, final Project project) {
        final LocalDateTime start = form.getStart();
        final LocalDateTime stop = form.getStop();
        return start.isAfter(projectUser.getEnterOn()) && start.isAfter(project.getStartDate()) && stop.isBefore(projectUser.getLeaveOn()) && stop.isBefore(project.getEndDate());
    }
    @Override
    public boolean validateTime(final TimeRecordUpdateForm form, final ProjectUser projectUser, final Project project) {
        final LocalDateTime start = form.getStart();
        final LocalDateTime stop = form.getStop();
        return start.isAfter(projectUser.getEnterOn()) && start.isAfter(project.getStartDate()) && stop.isBefore(projectUser.getLeaveOn()) && stop.isBefore(project.getEndDate());
    }
}
