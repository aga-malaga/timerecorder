package com.example.timerecorder.project.controller;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.project.dto.ProjectSearchCriteria;
import com.example.timerecorder.project.dto.ProjectAddForm;
import com.example.timerecorder.project.dto.ProjectDto;
import com.example.timerecorder.project.dto.ProjectForm;
import com.example.timerecorder.project.dto.ProjectRemoveForm;
import com.example.timerecorder.project.dto.ProjectUpdateForm;
import com.example.timerecorder.project.dto.ReportDto;
import com.example.timerecorder.project.dto.ReportForm;
import com.example.timerecorder.project.ProjectFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/projects")
class ProjectController {

    private final ProjectFacade projectService;

    @GetMapping
    public PageDTO getProjectsPaged(@RequestParam UUID managerUuid,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort) {
        return projectService.findProjects(managerUuid, page, size, sort);
    }

    @GetMapping("/filter")
    public PageDTO filterUsers(@RequestParam UUID managerUuid,
                               @RequestBody(required = false) ProjectSearchCriteria searchCriteria,
                               Pageable page) {
        return projectService.filterProjects(managerUuid, searchCriteria, page);
    }

    @GetMapping("/userreport")
    public ReportDto showUserReport(@RequestParam UUID managerUuid,
                                    @RequestBody @Valid ReportForm form) {
        return projectService.createUserReport(managerUuid, form);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestParam UUID managerUuid,
                                    @RequestBody @Valid ProjectForm projectForm) {
        return projectService.createProject(managerUuid, projectForm);
    }

    @PostMapping("/addUser")
    public void addUserToProject(@RequestParam UUID managerUuid,
                                 @RequestBody @Valid ProjectAddForm addForm) {
        projectService.addUserToProject(managerUuid, addForm);
    }

    @DeleteMapping("/removeUser")
    public void removeUserFromProject(@RequestParam UUID managerUuid,
                                      @RequestBody @Valid ProjectRemoveForm shiftForm) {
        projectService.removeUserFromProject(managerUuid, shiftForm);
    }

    @PutMapping
    public void updateProject(@RequestParam UUID managerUuid, @RequestBody @Valid ProjectUpdateForm updateForm) {
        projectService.editProject(managerUuid, updateForm);
    }

    @DeleteMapping
    public void deleteProject(@RequestParam UUID managerUuid, @RequestParam UUID uuid) {
        projectService.deleteProject(managerUuid, uuid);
    }
}
