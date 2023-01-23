package com.example.antologic.timeRecord.validateTime;

import com.example.antologic.project.Project;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.timeRecord.dto.TimeRecordForm;

import java.time.LocalDateTime;

public interface TimeValidator {

    boolean validateTime(TimeRecordForm form, ProjectUser projectUser, Project project);
}
