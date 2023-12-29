package com.example.timerecorder.project.controller;

import com.example.timerecorder.common.dto.PageDTO;
import com.example.timerecorder.project.ProjectFacade;
import com.example.timerecorder.project.dto.ProjectAddForm;
import com.example.timerecorder.project.dto.ProjectDto;
import com.example.timerecorder.project.dto.ProjectForm;
import com.example.timerecorder.project.dto.ProjectRemoveForm;
import com.example.timerecorder.project.dto.ProjectSearchCriteria;
import com.example.timerecorder.project.dto.ProjectUpdateForm;
import com.example.timerecorder.project.dto.ReportDto;
import com.example.timerecorder.project.dto.ReportForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/projects")
class ProjectController {

    private final ProjectFacade projectService;

    @GetMapping
    public PageDTO getProjectsPaged(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort) {
        return projectService.findProjects(page, size, sort);
    }

    @GetMapping("/filter")
    public PageDTO filterProjects(@RequestBody(required = false) ProjectSearchCriteria searchCriteria,
                                  Pageable page) {
        return projectService.filterProjects(searchCriteria, page);
    }

    @GetMapping("/userreport")
    public ReportDto showUserReport(@RequestBody @Valid ReportForm form) {
        return projectService.createUserReport(form);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody @Valid ProjectForm projectForm) {
        return projectService.createProject(projectForm);
    }

    @PostMapping("/addUser")
    public void addUserToProject(@RequestBody @Valid ProjectAddForm addForm) {
        projectService.addUserToProject(addForm);
    }

    @DeleteMapping("/removeUser")
    public void removeUserFromProject(@RequestBody @Valid ProjectRemoveForm shiftForm) {
        projectService.removeUserFromProject(shiftForm);
    }

    @PutMapping
    public void updateProject(@RequestBody @Valid ProjectUpdateForm updateForm) {
        projectService.editProject(updateForm);
    }

    @DeleteMapping
    public void deleteProject(@RequestParam UUID uuid) {
        projectService.deleteProject(uuid);
    }
}
