package org.internship.task_management.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.internship.task_management.entity.TaskStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusDTO {
    @NotNull
    private TaskStatus status;
}
