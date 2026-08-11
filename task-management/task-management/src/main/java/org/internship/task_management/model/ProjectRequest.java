package org.internship.task_management.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.internship.task_management.entity.ProjectStatus;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    private Long id;

    @NotBlank
    private String name;

    private String description;
    private ProjectStatus status;
}
