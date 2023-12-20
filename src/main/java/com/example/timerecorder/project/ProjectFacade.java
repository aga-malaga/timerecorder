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
    PageDTO findProjects(UUID managerUuid, int pageNo, int pageSize, String sortBy);
    ProjectDto createProject(UUID managerUuid, ProjectForm projectForm);
    void addUserToProject(UUID managerUuid, ProjectAddForm addForm);
    void removeUserFromProject(UUID managerUuid, ProjectRemoveForm removeForm);
    PageDTO filterProjects(UUID adminUuid, ProjectSearchCriteria searchCriteria, Pageable page);
    void editProject(UUID managerUuid, ProjectUpdateForm updateForm);
    void deleteProject(UUID managerUuid, UUID uuid);
    ReportDto createUserReport(UUID managerUuid, ReportForm form);
}