package com.example.antologic.service;

import com.example.antologic.common.AlreadyExistsException;
import com.example.antologic.common.ConflictException;
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
import com.example.antologic.project.dto.ProjectDTOBudget;
import com.example.antologic.project.dto.ProjectForm;
import com.example.antologic.project.dto.ProjectMapper;
import com.example.antologic.project.dto.ProjectShiftForm;
import com.example.antologic.project.dto.ProjectUpdateForm;
import com.example.antologic.project.report.ProjectReportDTO;
import com.example.antologic.project.report.ReportDTO;
import com.example.antologic.project.report.ReportForm;
import com.example.antologic.projectUser.ProjectUser;
import com.example.antologic.repository.ProjectRepository;
import com.example.antologic.repository.ProjectUserRepository;
import com.example.antologic.repository.TimeRecordRepository;
import com.example.antologic.repository.UserRepository;
import com.example.antologic.timeRecord.TimeRecord;
import com.example.antologic.user.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final TimeRecordRepository timeRecordRepository;

    private final ManagerValidator managerValidator;

    private final ProjectUserRepository projectUserRepository;

    public PageDTO findProjects(UUID managerUuid, int pageNo, int pageSize, String sortBy) {
        validate(managerUuid);

        Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        final List<ProjectDTOBudget> projectDTOPage = projectRepository.findAll(p)
                .stream().map(project -> ProjectMapper.toDtoBudget(project, countBudget(project)))
                .collect(Collectors.toList());
        final Page<ProjectDTOBudget> page = new PageImpl<>(projectDTOPage);
        return PageMapper.toDtoProject(page);
    }

    private void validate(final UUID managerUuid) {
        managerValidator.validateManager(managerUuid);
    }

    public BigDecimal countBudget(Project project) {

        final List<TimeRecord> timeRecordList = timeRecordRepository.findTimeRecordsByProject(project);

        final BigDecimal budget = timeRecordList.stream()
                .map(tr -> tr.getSalary().multiply(BigDecimal.valueOf(Duration.between(tr.getStart(), tr.getStop()).toHours())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return budget.multiply(BigDecimal.valueOf(100)).divide(project.getBudget(), RoundingMode.HALF_UP);

    }

    public ProjectDTO createProject(UUID managerUuid, ProjectForm projectForm) {
        validate(managerUuid);

        if (projectRepository.existsByName(projectForm.getName())) {
            throw new AlreadyExistsException("Project with name " + projectForm.getName() + " already exists");
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

        if (project.getStartDate().isAfter(addForm.getEnterOn()) || project.getEndDate().isBefore(addForm.getLeaveOn())) {
            throw new ConflictException("Project starts or ends in different time period");
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

        if (projectUserRepository.findByProjectAndUser(project, user).isEmpty()) {
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
        final List<ProjectDTOBudget> projectDtoBudgets = projectRepository.findAll(page).stream()
                .map(project -> ProjectMapper.toDtoBudget(project, countBudget(project)))
                .toList();

        Specification<Project> specification = new ProjectSpecification(searchCriteria, projectDtoBudgets);

        final List<ProjectDTOBudget> projectDTOBudgetsFiltered = projectRepository.findAll(specification, page).stream()
                .map(project -> ProjectMapper.toDtoBudget(project, countBudget(project)))
                .toList();

        final Page<ProjectDTOBudget> pages = new PageImpl<>(projectDTOBudgetsFiltered);

        return PageMapper.toDtoProject(pages);
    }

    @Override
    @Transactional
    public void editProject(final UUID managerUuid, final ProjectUpdateForm updateForm) {
        validate(managerUuid);

        final UUID uuid = updateForm.getProjectUuid();
        final Project project = projectRepository.findProjectByUuid(uuid).orElseThrow(() ->
                new NotFoundException("Project with id " + uuid + " not found"));

        if (projectRepository.existsByName(updateForm.getName())) {
            throw new AlreadyExistsException("Project with name " + updateForm.getName() + " already exists");
        }

        project.setName(updateForm.getName());
        project.setDescription(updateForm.getDescription());
        project.setStartDate(updateForm.getStart());
        project.setEndDate(updateForm.getStop());
        project.setBudget(updateForm.getBudget());
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

    public ReportDTO createUserReport(UUID managerUuid, ReportForm form) {
        validate(managerUuid);

        final User user = userRepository.findByUuid(form.getUserUuid()).orElseThrow(() ->
                new NotFoundException("User does not exist"));

        LocalDateTime period = LocalDateTime.now();
        switch (form.getTimePeriod()) {
            case WEEK -> period = period.minus(Period.ofDays(7));
            case MONTH -> period = period.minus(Period.ofMonths(1));
            case YEAR -> period = period.minus(Period.ofYears(1));
        }
        List<TimeRecord> timeRecordList = timeRecordRepository.findTimeRecordsByUser(user, period);

        final BigDecimal totalCost = timeRecordList.stream()
                .map(tr -> tr.getSalary().multiply(BigDecimal.valueOf(Duration.between(tr.getStart(), tr.getStop()).toHours())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final List<ProjectUser> projectUsers = projectUserRepository.findProjectUsersByUser(user);

        final List<ProjectReportDTO> dtos = projectUsers.stream()
                .map(projectUser -> new ProjectReportDTO(
                        projectUser.getProject().getName(),
                        timeRecordRepository.findTimeRecordsByProjectUserAndSumHours(projectUser),
                        timeRecordRepository.findTimeRecordsByProjectUserAndSumHCost(projectUser))).toList();

        return new ReportDTO(totalCost, dtos);
    }
}

