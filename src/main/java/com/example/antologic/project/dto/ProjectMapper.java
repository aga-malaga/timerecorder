package com.example.antologic.project.dto;

import com.example.antologic.project.Project;

import java.util.stream.Collectors;

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
                project.getUsers().stream().map(user -> {
                    return new ProjectUserDTO(user.getUuid(), user.getName(), user.getSurname());
                }).collect(Collectors.toSet())
        );
    }
}
