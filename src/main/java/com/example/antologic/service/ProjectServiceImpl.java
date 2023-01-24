package com.example.antologic.service;

import com.example.antologic.common.AlreadyExistsException;
import com.example.antologic.common.NoContentException;
import com.example.antologic.common.NotFoundException;
import com.example.antologic.common.dto.PageDTO;
import com.example.antologic.common.dto.PageMapper;
import com.example.antologic.customSecurity.ManagerValidator;
import com.example.antologic.filter.ProjectSearchCriteria;
import com.example.antologic.project.Project;
import com.example.antologic.project.ProjectSpecification;
import com.example.antologic.project.dto.ProjectAddForm;
import com.example.antologic.project.dto.ProjectDTO;
import com.example.antologic.project.dto.ProjectForm;
import com.example.antologic.project.dto.ProjectMapper;
import com.example.antologic.project.dto.ProjectShiftForm;
import com.example.antologic.project.dto.ProjectUpdateForm;
import com.example.antologic.repository.ProjectRepository;
import com.example.antologic.repository.ProjectUserRepository;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final ManagerValidator managerValidator;

    private final ProjectUserRepository projectUserRepository;

    public PageDTO findProjects(UUID managerUuid, int pageNo, int pageSize, String sortBy) {
        validate(managerUuid);

        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<ProjectDTO> projectDTOPage = projectRepository.findAll(p).map(ProjectMapper::toDto);
        return PageMapper.toDtoProject(projectDTOPage);
    }

    private void validate(final UUID managerUuid) {
        managerValidator.validateManager(managerUuid);
    }

    public ProjectDTO createProject(UUID managerUuid, ProjectForm projectForm) {
        validate(managerUuid);

        if (projectRepository.existsByName(projectForm.getName())) {
            throw new AlreadyExistsException("Project with this name already exists");
        }
        Project project = ProjectMapper.toProject(projectForm);
        projectRepository.save(project);

        return ProjectMapper.toDto(project);
    }

    @Transactional
    public boolean addUserToProject(UUID managerUuid, ProjectAddForm addForm) {
        validate(managerUuid);

        final Project project = projectRepository.findProjectByUuid(addForm.getProjectUuid()).orElseThrow(() ->
                new NotFoundException("Project does not exists"));

        final User user = userRepository.findByUuid(addForm.getUserUuid()).orElseThrow(() ->
                new NotFoundException("User does not exist"));

        if (projectUserRepository.findByProjectAndUser(project, user).isPresent()) {
            throw new AlreadyExistsException("This user is already in the project");
        }

        project.addUser(user, addForm.getEnterOn(), addForm.getLeaveOn());

        return true;
    }

    @Transactional
    public boolean removeUserFromProject(UUID managerUuid, ProjectShiftForm removeForm) {
        validate(managerUuid);

        final Project project = projectRepository.findProjectByUuid(removeForm.getProjectUuid()).orElseThrow(() ->
                new NotFoundException("Project does not exists"));

        final User user = userRepository.findByUuid(removeForm.getUserUuid()).orElseThrow(() ->
                new NotFoundException("User does not exist"));

        if (!projectUserRepository.findByProjectAndUser(project, user).isPresent()) {
            throw new NotFoundException("This user is not in the project");
        }

        project.removeUser(user);

        return true;
    }

    @Override
    public PageDTO filterProjects(UUID managerUuid, ProjectSearchCriteria searchCriteria, Pageable page) {
        validate(managerUuid);

        if (searchCriteria == null) {
            throw new NoContentException("No criteria included");
        }
        Specification<Project> specification = new ProjectSpecification(searchCriteria);

        final Page<ProjectDTO> pageUserDto = projectRepository.findAll(specification, page).map(ProjectMapper::toDto);
        return PageMapper.toDtoProject(pageUserDto);
    }

    @Override
    public void editProject(final UUID managerUuid, final ProjectUpdateForm updateForm) {
        validate(managerUuid);

        final UUID uuid = updateForm.getProjectUuid();
        final Project project = projectRepository.findProjectByUuid(uuid).orElseThrow(() ->
                new NotFoundException("Project with id " + uuid + " not found"));

        project.setName(updateForm.getName());
        project.setDescription(updateForm.getDescription());
        project.setStartDate(updateForm.getStart());
        project.setEndDate(updateForm.getStop());
        project.setBudget(updateForm.getBudget());
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void deleteProject(UUID managerUuid, UUID uuid) {
        validate(managerUuid);

        if (!projectRepository.existsByUuid(uuid)) {
            throw new NotFoundException("Project with id " + uuid + " not found");
        }
        projectRepository.removeProjectByUuid(uuid);
    }
}
