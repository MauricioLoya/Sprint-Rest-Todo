package com.sprint.todo.SprintRestTodo.domain.todo.dto;

import java.time.LocalDateTime;

public record TodoDataDetails(
        Long id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
