package com.example.timerecorder.timeRecord.validateTime;

import com.example.timerecorder.project.Project;
import com.example.timerecorder.projectUser.domain.ProjectUser;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
final class TimeValidatorImpl implements TimeValidator {

    @Override
    public boolean validateTime(final LocalDateTime start, final LocalDateTime stop, final ProjectUser projectUser, final Project project) {
        return start.isAfter(projectUser.getEnterOn().atStartOfDay())
                && start.isAfter(project.getStartDate().atStartOfDay())
                && stop.isBefore(projectUser.getLeaveOn().atStartOfDay())
                && stop.isBefore(project.getEndDate().atStartOfDay());
    }
}