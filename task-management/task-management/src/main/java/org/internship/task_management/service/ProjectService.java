package org.internship.task_management.service;

import org.internship.task_management.entity.Project;
import org.internship.task_management.exception.InvalidProjectNameException;
import org.internship.task_management.model.ProjectRequest;
import org.internship.task_management.model.ProjectResponse;
import org.internship.task_management.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(ProjectRequest dto){
        if(dto.getName().trim().isBlank() || dto.getName() == null){
            throw new InvalidProjectNameException("Project name should not be empty");
        }
        Project project = new Project(null, dto.getName().trim(), dto.getDescription(), dto.getStatus());
        Project savedProject = projectRepository.save(project);
        return ProjectResponse.builder()
                .id(savedProject.getId())
                .name(savedProject.getName())
                .description(savedProject.getDescription())
                .status(savedProject.getStatus())
                .build();
    }
}
