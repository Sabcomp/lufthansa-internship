package org.internship.task_management.controller;

import org.internship.task_management.model.ProjectRequest;
import org.internship.task_management.model.ProjectResponse;
import org.internship.task_management.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(ProjectRequest request){
        return new ResponseEntity<>(projectService.createProject(request), HttpStatus.CREATED);
    }

}
