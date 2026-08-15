package org.internship.task_management.controller;

import jakarta.validation.Valid;
import org.internship.task_management.entity.TaskStatus;
import org.internship.task_management.model.TaskRequest;
import org.internship.task_management.model.TaskResponse;
import org.internship.task_management.model.TaskStatusDTO;
import org.internship.task_management.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long projectId, @Valid @RequestBody TaskRequest request){
        return new ResponseEntity<>(taskService.createTask(projectId, request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id){
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @Valid @RequestBody TaskStatusDTO dto){
        return new ResponseEntity<>(taskService.updateTask(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "projectId")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(@RequestParam Long projectId) {
        return new ResponseEntity<>(taskService.getTasksByProject(projectId), HttpStatus.OK);
    }

    @GetMapping(params = "status")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(@RequestParam TaskStatus status) {
        return new ResponseEntity<>(taskService.getTasksByStatus(status), HttpStatus.OK);
    }

}
