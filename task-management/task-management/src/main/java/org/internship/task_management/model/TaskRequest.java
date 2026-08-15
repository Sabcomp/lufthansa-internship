package org.internship.task_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.internship.task_management.entity.TaskPriority;
import org.internship.task_management.entity.TaskStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotBlank
    private String title;

    private String description;
    private TaskStatus status;
    private TaskPriority priority;
}
