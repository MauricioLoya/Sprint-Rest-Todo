package com.sprint.todo.SprintRestTodo.domain.todo.dto;

import com.sprint.todo.SprintRestTodo.domain.todo.model.Todo;
import com.sprint.todo.SprintRestTodo.domain.user.model.User;

import java.time.LocalDateTime;

public record TodoDataList(
        Long id,
        String title,
        LocalDateTime createdAt,
        boolean completed) {

    public TodoDataList(Todo todo) {
        this(todo.getId(), todo.getTitle(), todo.getCreatedAt(), todo.isCompleted());
    }
}
