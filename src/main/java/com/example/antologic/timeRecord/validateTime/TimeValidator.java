package com.example.antologic.timeRecord.validateTime;

import com.example.antologic.project.Project;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.timeRecord.dto.TimeRecordForm;
import com.example.antologic.timeRecord.dto.TimeRecordUpdateForm;

public interface TimeValidator {

    boolean validateTime(TimeRecordForm form, ProjectUser projectUser, Project project);

    boolean validateTime(TimeRecordUpdateForm form, ProjectUser projectUser, Project project);
}
