package com.example.antologic.service;

import com.example.antologic.common.AlreadyExistsException;
import com.example.antologic.customSecurity.ManagerValidator;
import com.example.antologic.project.Project;
import com.example.antologic.project.dto.ProjectDTO;
import com.example.antologic.project.dto.ProjectForm;
import com.example.antologic.project.dto.ProjectMapper;
import com.example.antologic.repository.ProjectRepository;
import com.example.antologic.user.dto.UserDTO;
import com.example.antologic.user.dto.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@AllArgsConstructor
class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    private final ManagerValidator managerValidator;

    private void validate(final UUID managerUuid) {
        managerValidator.validateManager(managerUuid);
    }

    public Page<ProjectDTO> findProjects(UUID managerUuid, int pageNo, int pageSize, String sortBy) {
        validate(managerUuid);

        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return projectRepository.findAll(p).map(ProjectMapper::toDto);
    }

    public ProjectDTO createProject(UUID managerUuid, ProjectForm projectForm){
        validate(managerUuid);

        if (projectRepository.existsByName(projectForm.getName())) {
            throw new AlreadyExistsException("Project with this name already exists");
        }
        Project project = ProjectMapper.toProject(projectForm);
        projectRepository.save(project);

        return ProjectMapper.toDto(project);
    }
}
