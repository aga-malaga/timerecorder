package com.example.antologic.service;

import com.example.antologic.project.dto.ProjectAddForm;
import com.example.antologic.project.dto.ProjectDTO;
import com.example.antologic.project.dto.ProjectForm;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProjectService {

    Page<ProjectDTO> findProjects(UUID managerUuid, int pageNo, int pageSize, String sortBy);

    ProjectDTO createProject(UUID managerUuid, ProjectForm projectForm);

    boolean addUserToProject(UUID managerUuid, ProjectAddForm addForm);
}
