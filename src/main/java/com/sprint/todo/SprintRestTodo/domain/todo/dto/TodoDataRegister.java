package com.sprint.todo.SprintRestTodo.domain.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record TodoDataRegister(
        @NotBlank
        String title,
        String description,
        boolean completed
        ) {
}
