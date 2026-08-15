package org.internship.task_management.service;

import org.internship.task_management.entity.Project;
import org.internship.task_management.entity.ProjectStatus;
import org.internship.task_management.entity.Task;
import org.internship.task_management.entity.TaskStatus;
import org.internship.task_management.exception.InvalidTaskArgumentException;
import org.internship.task_management.exception.ProjectNotFoundException;
import org.internship.task_management.exception.TaskAssignmentException;
import org.internship.task_management.exception.TaskNotFoundException;
import org.internship.task_management.model.TaskRequest;
import org.internship.task_management.model.TaskResponse;
import org.internship.task_management.model.TaskStatusDTO;
import org.internship.task_management.repository.ProjectRepository;
import org.internship.task_management.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponse createTask(Long projectId, TaskRequest dto) {
        // check whether project exists
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found"));
        // cannot add tasks to completed project
        if (project.getStatus().equals(ProjectStatus.COMPLETED))
            throw new TaskAssignmentException("Task cannot be assigned to project with status " + ProjectStatus.COMPLETED);
        // task title can't be empty
        if (dto.getTitle().trim().isBlank() || dto.getTitle() == null)
            throw new InvalidTaskArgumentException("Task title should not be empty");

        Task task = new Task(null, dto.getTitle().trim(), dto.getDescription(), dto.getStatus(), dto.getPriority(), project);
        Task savedTask = taskRepository.save(task);
        return new TaskResponse(
                savedTask.getId(), savedTask.getTitle(), savedTask.getDescription(),
                savedTask.getStatus(), savedTask.getPriority(), savedTask.getProject().getId()
        );
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> new TaskResponse(
                        task.getId(), task.getTitle(), task.getDescription(),
                        task.getStatus(), task.getPriority(), task.getProject().getId()))
                .toList();

    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        return new TaskResponse(
                task.getId(), task.getTitle(), task.getDescription(),
                task.getStatus(), task.getPriority(), task.getProject().getId());
    }

    public TaskResponse updateTask(Long id, TaskStatusDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        task.setStatus(dto.getStatus());
        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(
                updatedTask.getId(), updatedTask.getTitle(), updatedTask.getDescription(),
                updatedTask.getStatus(), updatedTask.getPriority(), updatedTask.getProject().getId());
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        taskRepository.delete(task);
    }

    public List<TaskResponse> getTasksByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found"));
        return taskRepository.findByProject(project).stream()
                .map(task -> new TaskResponse(
                        task.getId(), task.getTitle(), task.getDescription(),
                        task.getStatus(), task.getPriority(), task.getProject().getId()))
                .toList();
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status){
        if (status != TaskStatus.COMPLETED && status != TaskStatus.TODO && status != TaskStatus.IN_PROGRESS)
            throw new InvalidTaskArgumentException("Task status can be " + TaskStatus.TODO + ", " + TaskStatus.IN_PROGRESS + ", " + TaskStatus.COMPLETED);

        return taskRepository.findByStatus(status).stream()
                .map(task -> new TaskResponse(
                        task.getId(), task.getTitle(), task.getDescription(),
                        task.getStatus(), task.getPriority(), task.getProject().getId()))
                .toList();
    }
}
