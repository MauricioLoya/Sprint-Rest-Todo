package com.sprint.todo.SprintRestTodo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDataRegister(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String name,
        @NotBlank
        String lastName,
        @NotBlank
        String password,
        @NotBlank
        String passwordConfirmation
        ) {
}