package com.example.timerecorder.project;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.project.dto.ProjectSearchCriteria;
import com.example.timerecorder.project.dto.ProjectAddForm;
import com.example.timerecorder.project.dto.ProjectDto;
import com.example.timerecorder.project.dto.ProjectForm;
import com.example.timerecorder.project.dto.ProjectRemoveForm;
import com.example.timerecorder.project.dto.ProjectUpdateForm;
import com.example.timerecorder.project.dto.ReportDto;
import com.example.timerecorder.project.dto.ReportForm;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProjectFacade {
    PageDTO findProjects(int pageNo, int pageSize, String sortBy);
    ProjectDto createProject(ProjectForm projectForm);
    void addUserToProject(ProjectAddForm addForm);
    void removeUserFromProject(ProjectRemoveForm removeForm);
    PageDTO filterProjects(ProjectSearchCriteria searchCriteria, Pageable page);
    void editProject(ProjectUpdateForm updateForm);
    void deleteProject(UUID uuid);
    ReportDto createUserReport(ReportForm form);
}