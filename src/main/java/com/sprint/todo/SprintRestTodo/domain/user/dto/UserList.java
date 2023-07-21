package com.sprint.todo.SprintRestTodo.domain.user.dto;

import com.sprint.todo.SprintRestTodo.domain.user.model.User;

public record UserList(
        Long id,
        String email,
        String name,
        String lastName,
        boolean active) {

    public UserList(User user) {
        this(user.getId(), user.getEmail(),user.getName(),user.getLastName(), user.isActive());
    }
}
