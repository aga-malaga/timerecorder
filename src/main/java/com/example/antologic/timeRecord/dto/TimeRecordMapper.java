package com.example.antologic.timeRecord.dto;

import com.example.antologic.project.Project;
import com.example.antologic.repository.ProjectRepository;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.timeRecord.TimeRecord;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeRecordMapper {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    public static Project toProject(TimeRecordForm form, Project project) {
        return new Project();
    }
}
