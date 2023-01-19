package com.example.antologic.controller;

import com.example.antologic.project.dto.ProjectDTO;
import com.example.antologic.project.dto.ProjectForm;
import com.example.antologic.project.dto.ProjectShiftForm;
import com.example.antologic.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/projects")
class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public Page<ProjectDTO> getProjectsPaged(@RequestParam UUID managerUuid,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "uuid") String sort) {
        return projectService.findProjects(managerUuid, page, size, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTO createProject(@RequestParam UUID managerUuid,
                                    @RequestBody @Valid ProjectForm projectForm) {
        return projectService.createProject(managerUuid, projectForm);
    }

    @PostMapping("/addUser")
    public void addUserToProject(@RequestParam UUID managerUuid,
                                 @RequestBody @Valid ProjectShiftForm shiftForm) {
        projectService.addUserToProject(managerUuid, shiftForm);
    }

    @DeleteMapping("/removeUser")
    public void removeUserFromProject(@RequestParam UUID managerUuid,
                                      @RequestBody @Valid ProjectShiftForm shiftForm) {
        projectService.removeUserFromProject(managerUuid, shiftForm);
    }


}
