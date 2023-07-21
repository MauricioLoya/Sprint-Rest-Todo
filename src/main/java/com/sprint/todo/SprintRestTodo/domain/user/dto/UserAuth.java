package com.sprint.todo.SprintRestTodo.domain.user.dto;

import java.util.Optional;

public record UserAuth(String email, String password, Optional<String> token) {
    // constructor just with token
    public UserAuth(String token) {
        this(null, null, Optional.of(token));
    }
}
