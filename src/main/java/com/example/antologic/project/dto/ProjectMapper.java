package com.example.antologic.project.dto;

import com.example.antologic.project.Project;

public class ProjectMapper {

    public static Project toProject(ProjectForm projectForm) {
        return new Project(
                projectForm.getName(),
                projectForm.getDescription(),
                projectForm.getStart(),
                projectForm.getStop(),
                projectForm.getBudget()
        );
    }

    public static ProjectDTO toDto(Project project) {
        return new ProjectDTO(
                project.getUuid(),
                project.getName(),
                project.getDescription(),
                project.getStart(),
                project.getStop(),
                project.getBudget(),
                project.getUsers()
        );
    }
}
