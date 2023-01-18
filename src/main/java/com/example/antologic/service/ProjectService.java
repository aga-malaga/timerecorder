package com.example.antologic.service;

import com.example.antologic.project.dto.ProjectDTO;
import com.example.antologic.project.dto.ProjectForm;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProjectService {

    ProjectDTO createProject(UUID managerUuid, ProjectForm projectForm);

    Page<ProjectDTO> findProjects(UUID manageUuid, int pageNo, int pageSize, String sortBy);
}
