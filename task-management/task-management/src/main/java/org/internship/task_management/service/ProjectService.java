package org.internship.task_management.service;

import org.internship.task_management.entity.Project;
import org.internship.task_management.exception.InvalidProjectNameException;
import org.internship.task_management.exception.ProjectNotFoundException;
import org.internship.task_management.model.ProjectRequest;
import org.internship.task_management.model.ProjectResponse;
import org.internship.task_management.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return new ProjectResponse(savedProject.getId(), savedProject.getName(), savedProject.getDescription(), savedProject.getStatus());
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(project -> new ProjectResponse(project.getId(), project.getName(), project.getDescription(), project.getStatus()))
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        return new ProjectResponse(project.getId(), project.getName(), project.getDescription(), project.getStatus());
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        projectRepository.delete(project);
    }
}
