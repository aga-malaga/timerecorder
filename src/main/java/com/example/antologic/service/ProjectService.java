package com.example.antologic.service;

import com.example.antologic.common.dto.PageDTO;
import com.example.antologic.filter.ProjectSearchCriteria;
import com.example.antologic.project.dto.ProjectAddForm;
import com.example.antologic.project.dto.ProjectDTO;
import com.example.antologic.project.dto.ProjectForm;
import com.example.antologic.project.dto.ProjectShiftForm;
import com.example.antologic.project.dto.ProjectUpdateForm;
import com.example.antologic.project.report.ProjectReportDTO;
import com.example.antologic.project.report.ReportForm;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProjectService {

    PageDTO findProjects(UUID managerUuid, int pageNo, int pageSize, String sortBy);

    ProjectDTO createProject(UUID managerUuid, ProjectForm projectForm);

    boolean addUserToProject(UUID managerUuid, ProjectAddForm addForm);

    boolean removeUserFromProject(UUID managerUuid, ProjectShiftForm removeForm);

    PageDTO filterProjects(UUID adminUuid, ProjectSearchCriteria searchCriteria, Pageable page);

    void editProject(UUID managerUuid, ProjectUpdateForm updateForm);

    void deleteProject(UUID managerUuid, UUID uuid);

    ProjectReportDTO createUserReport(UUID managerUuid, ReportForm form);
}
