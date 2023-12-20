package com.example.timerecorder.timeRecord.validateTime;

import com.example.timerecorder.project.Project;
import com.example.timerecorder.projectUser.domain.ProjectUser;

import java.time.LocalDateTime;

public interface TimeValidator {
    boolean validateTime(LocalDateTime start, LocalDateTime stop, ProjectUser projectUser, Project project);
}