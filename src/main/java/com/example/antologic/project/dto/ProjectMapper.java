package com.example.antologic.project.dto;

import com.example.antologic.project.Project;

public class ProjectMapper {

    public static Project toProject(ProjectForm projectForm) {
        Project project = new Project();
        project.setName(projectForm.getName());
        project.setDescription(projectForm.getDescription());
        project.setStart(projectForm.getStart());
        project.setStop(projectForm.getStop());
        project.setBudget(projectForm.getBudget());
        return project;
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
