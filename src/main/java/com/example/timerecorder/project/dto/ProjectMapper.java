package com.example.timerecorder.project.dto;

import com.example.timerecorder.project.Project;
import com.example.timerecorder.projectUser.domain.ProjectUser;

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

    public static void update(Project project, ProjectUpdateForm updateForm) {
        project.setName(updateForm.name());
        project.setDescription(updateForm.description());
        project.setStartDate(updateForm.start());
        project.setEndDate(updateForm.start());
        project.setBudget(updateForm.budget());
    }


    public static ProjectDto toDto(Project project) {
        return new ProjectDto(
                project.getUuid(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getBudget(),
                projectUserDTO(project));
    }

    public static ProjectDtoBudget toDtoBudget(Project project, BigDecimal budget) {
        return new ProjectDtoBudget(
                project.getUuid(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getBudget(),
                budget,
                projectUserDTO(project));
    }

    private static List<ProjectUserDTO> projectUserDTO(Project project) {
        return project.getUsers().stream()
                .map(ProjectMapper::toProjectUser)
                .toList();
    }

    private static ProjectUserDTO toProjectUser(final ProjectUser user) {
        return new ProjectUserDTO(user.getUser().getUuid(), user.getUser().getName(), user.getUser().getSurname());
    }
}