package com.example.timerecorder.project.domain;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.common.exception.AlreadyExistsException;
import com.example.timerecorder.common.exception.ConflictException;
import com.example.timerecorder.common.exception.NoContentException;
import com.example.timerecorder.common.exception.NotFoundException;
import com.example.timerecorder.customSecurity.ManagerValidator;
import com.example.timerecorder.project.Project;
import com.example.timerecorder.project.ProjectFacade;
import com.example.timerecorder.project.ProjectSpecification;
import com.example.timerecorder.project.dto.ProjectAddForm;
import com.example.timerecorder.project.dto.ProjectDto;
import com.example.timerecorder.project.dto.ProjectDtoBudget;
import com.example.timerecorder.project.dto.ProjectForm;
import com.example.timerecorder.project.dto.ProjectRemoveForm;
import com.example.timerecorder.project.dto.ProjectReportDto;
import com.example.timerecorder.project.dto.ProjectSearchCriteria;
import com.example.timerecorder.project.dto.ProjectUpdateForm;
import com.example.timerecorder.project.dto.ReportDto;
import com.example.timerecorder.project.dto.ReportForm;
import com.example.timerecorder.projectUser.domain.ProjectUser;
import com.example.timerecorder.projectUser.domain.ProjectUserRepository;
import com.example.timerecorder.timeRecord.domain.TimeRecord;
import com.example.timerecorder.timeRecord.domain.TimeRecordRepository;
import com.example.timerecorder.user.domain.User;
import com.example.timerecorder.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
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

import static com.example.timerecorder.common.dto.PageMapper.toDtoProject;
import static com.example.timerecorder.project.dto.ProjectMapper.toDto;
import static com.example.timerecorder.project.dto.ProjectMapper.toDtoBudget;
import static com.example.timerecorder.project.dto.ProjectMapper.toProject;
import static com.example.timerecorder.project.dto.ProjectMapper.update;

@Service
@RequiredArgsConstructor
class ProjectServiceImpl implements ProjectFacade {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TimeRecordRepository timeRecordRepository;
    private final ProjectUserRepository projectUserRepository;
    private final ManagerValidator managerValidator;

    @Transactional
    @Override
    public PageDTO findProjects(UUID managerUuid, int pageNo, int pageSize, String sortBy) {
        validate(managerUuid);

        Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        final List<ProjectDtoBudget> projectDTOPage = projectRepository.findAll(pageRequest).stream()
                .map(project -> toDtoBudget(project, countBudget(project)))
                .toList();

        return toDtoProject(new PageImpl<>(projectDTOPage));
    }

    @Transactional
    @Override
    public ProjectDto createProject(UUID managerUuid, ProjectForm projectForm) {
        validate(managerUuid);
        verifyIfNameUnique(projectForm.getName());

        Project project = toProject(projectForm);
        projectRepository.save(project);

        return toDto(project);
    }

    @Transactional
    @Override
    public void addUserToProject(UUID managerUuid, ProjectAddForm addForm) {
        validate(managerUuid);

        final Project project = findProject(addForm.projectUuid());
        final User user = findUser(addForm.userUuid());

        verifyIfUserNotInProject(project, user);
        verifyProjectStartAndEndDate(addForm, project);

        project.addUser(user, addForm.enterOn(), addForm.leaveOn());
    }

    @Transactional
    @Override
    public void removeUserFromProject(UUID managerUuid, ProjectRemoveForm removeForm) {
        validate(managerUuid);

        final Project project = findProject(removeForm.projectUuid());
        final User user = findUser(removeForm.userUuid());

        verifyIfUserInProject(project, user);

        project.removeUser(user);
    }

    @Transactional
    @Override
    public PageDTO filterProjects(UUID managerUuid, ProjectSearchCriteria searchCriteria, Pageable page) {
        validate(managerUuid);
        if (searchCriteria == null) {
            throw new NoContentException("No criteria included");
        }

        final List<ProjectDtoBudget> projectDtoBudgets = projectRepository.findAll(page).stream()
                .map(project -> toDtoBudget(project, countBudget(project)))
                .toList();

        Specification<Project> specification = new ProjectSpecification(searchCriteria, projectDtoBudgets);

        final List<ProjectDtoBudget> projectDtoBudgetsFiltered = projectRepository.findAll(specification, page).stream()
                .map(project -> toDtoBudget(project, countBudget(project)))
                .toList();

        return toDtoProject(new PageImpl<>(projectDtoBudgetsFiltered));
    }

    @Transactional
    @Override
    public void editProject(final UUID managerUuid, final ProjectUpdateForm updateForm) {
        validate(managerUuid);

        final Project project = findProject(updateForm.projectUuid());

        verifyIfNameUnique(updateForm.name());

        update(project, updateForm);
        projectRepository.save(project);
    }

    @Transactional
    @Override
    public void deleteProject(UUID managerUuid, UUID uuid) {
        validate(managerUuid);
        verifyIfProjectExists(uuid);
        projectRepository.removeProjectByUuid(uuid);
    }

    @Transactional
    @Override
    public ReportDto createUserReport(UUID managerUuid, ReportForm form) {
        validate(managerUuid);

        final User user = findUser(form.userUuid());

        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime period = findPeriod(form, now);

        List<TimeRecord> timeRecordList = timeRecordRepository.findTimeRecordsByUser(user, period, now);

        final BigDecimal totalCost = timeRecordList.stream()
                .map(this::calculateTotalSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final List<ProjectUser> projectUsers = projectUserRepository.findProjectUsersByUser(user);

        final List<ProjectReportDto> projectReports = projectUsers.stream()
                .map(projectUser -> new ProjectReportDto(
                        projectUser.getProject().getName(),
                        timeRecordRepository.findTimeRecordsByProjectUserAndSumHours(projectUser, period, now),
                        timeRecordRepository.findTimeRecordsByProjectUserAndSumHCost(projectUser, period, now))).toList();

        return new ReportDto(user.getName(), user.getSurname(), totalCost, projectReports);
    }

    private LocalDateTime findPeriod(final ReportForm form, final LocalDateTime now) {
        return switch (form.timePeriod()) {
            case WEEK -> now.minus(Period.ofDays(7));
            case MONTH -> now.minus(Period.ofMonths(1));
            case YEAR -> now.minus(Period.ofYears(1));
        };
    }

    private void verifyIfProjectExists(final UUID uuid) {
        if (!projectRepository.existsByUuid(uuid)) {
            throw new NotFoundException("Project with id " + uuid + " not found");
        }
    }

    private void verifyIfUserInProject(final Project project, final User user) {
        if (projectUserRepository.findByProjectAndUser(project, user).isEmpty()) {
            throw new NotFoundException("This user is not in the project");
        }
    }

    private Project findProject(final UUID projectUuid) {
        return projectRepository.findProjectByUuid(projectUuid).orElseThrow(() ->
                new NotFoundException("Project does not exists"));
    }

    private User findUser(final UUID userUuid) {
        return userRepository.findByUuid(userUuid).orElseThrow(() ->
                new NotFoundException("User does not exist"));
    }

    private void verifyIfUserNotInProject(final Project project, final User user) {
        if (projectUserRepository.findByProjectAndUser(project, user).isPresent()) {
            throw new AlreadyExistsException("This user is already in the project");
        }
    }

    private static void verifyProjectStartAndEndDate(final ProjectAddForm addForm, final Project project) {
        if (project.getStartDate().isAfter(addForm.enterOn()) || project.getEndDate().isBefore(addForm.leaveOn())) {
            throw new ConflictException("Project starts or ends in different time period");
        }
    }

    private void verifyIfNameUnique(final String name) {
        if (projectRepository.existsByName(name)) {
            throw new AlreadyExistsException("Project with name " + name + " already exists");
        }
    }

    private void validate(final UUID managerUuid) {
        managerValidator.validateManager(managerUuid);
    }

    private BigDecimal countBudget(Project project) {

        final List<TimeRecord> timeRecordList = timeRecordRepository.findTimeRecordsByProject(project);

        final BigDecimal budget = timeRecordList.stream()
                .map(this::calculateTotalSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return budget.multiply(BigDecimal.valueOf(100)).divide(project.getBudget(), RoundingMode.HALF_UP);

    }

    private BigDecimal calculateTotalSalary(final TimeRecord timeRecord) {
        return timeRecord.getSalary().multiply(calculateWorkedHours(timeRecord));
    }

    private BigDecimal calculateWorkedHours(final TimeRecord timeRecord) {
        return BigDecimal.valueOf(Duration.between(timeRecord.getStart(), timeRecord.getStop()).toHours());
    }
}