package com.example.antologic.project.dto;

import com.example.antologic.project.Project;

import java.math.BigDecimal;
import java.util.List;

public class ProjectMapper {

    public static Project toProject(ProjectForm projectForm) {
        Project project = new Project();
        project.setName(projectForm.getName());
        project.setDescription(projectForm.getDescription());
        project.setStartDate(projectForm.getStartDate());
        project.setEndDate(projectForm.getEndDate());
        project.setBudget(projectForm.getBudget());
        return project;
    }


    public static ProjectDTO toDto(Project project) {
        return new ProjectDTO(
                project.getUuid(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getBudget(),
                projectUserDTO(project)
        );
    }

    public static List<ProjectUserDTO> projectUserDTO(Project project) {
        return project.getUsers().stream()
                .map(pu -> {
                    return new ProjectUserDTO(pu.getUser().getUuid(), pu.getUser().getName(), pu.getUser().getSurname());
                }).toList();
    }

    public static ProjectDTOBudget toDtoBudget(Project project, BigDecimal budget) {
        return new ProjectDTOBudget(
                project.getUuid(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getBudget(),
                budget,
                projectUserDTO(project)
        );
    }
}
