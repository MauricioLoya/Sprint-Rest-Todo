package com.sprint.todo.SprintRestTodo.domain.user.dto;

public record UserDataDetails
    (
        String email,
        String name,
        String lastName,
        Boolean active

    ) {

}
