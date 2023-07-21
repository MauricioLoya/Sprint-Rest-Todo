package com.sprint.todo.SprintRestTodo.controller;

import com.sprint.todo.SprintRestTodo.domain.user.UserRepository;
import com.sprint.todo.SprintRestTodo.domain.user.dto.UserDataDetails;
import com.sprint.todo.SprintRestTodo.domain.user.dto.UserList;
import com.sprint.todo.SprintRestTodo.domain.user.model.User;
import com.sprint.todo.SprintRestTodo.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Controller responsible for user operations")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Get all users", description = "List the users registered in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @GetMapping
    public ResponseEntity<Page<UserList>> getAllUsers(@PageableDefault(size = 10, sort = "email") Pageable pageable) {
        return ResponseEntity.ok(repository.findAllByActiveTrue(pageable).map(UserList::new));
    }

    @Operation(summary = "Get a user", description = "Get a specific user by id")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the user")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserDataDetails> getOneUser(@PathVariable Long id) {
        User user = repository.findById(id).orElseThrow();
        return ResponseEntity.ok(user.toUserDataDetails());
    }

    @Operation(summary = "Update a user", description = "Update the authenticated user's data")
    @ApiResponse(responseCode = "200", description = "Successfully updated the user")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping
    @Transactional
    public ResponseEntity<UserDataDetails> updateOwnUser(
            @RequestBody @Valid UserDataDetails data,
            @RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        User user = repository.findById(userId).orElseThrow();
        user.updateData(data);
        repository.save(user);
        return ResponseEntity.ok(user.toUserDataDetails());
    }

    @Operation(summary = "Delete a user", description = "Delete the authenticated user")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the user")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping
    @Transactional
    public ResponseEntity deleteOwnUser(
            @RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        User user = repository.findById(userId).orElseThrow();
        user.delete();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a user by ID", description = "Update a specific user's data")
    @ApiResponse(responseCode = "200", description = "Successfully updated the user")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserDataDetails> updateOwnUser(
            @RequestBody @Valid UserDataDetails data, @PathVariable Long id) {
        User user = repository.findById(id).orElseThrow();
        user.updateData(data);
        repository.save(user);
        return ResponseEntity.ok(user.toUserDataDetails());
    }

    @Operation(summary = "Delete a user by ID", description = "Delete a specific user")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the user")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOwnUser(
            @PathVariable Long id) {
        User user = repository.findById(id).orElseThrow();
        user.delete();
        return ResponseEntity.noContent().build();
    }

    private Long getUserId(String token) {
        return tokenService.getUserId(token.replace("Bearer ", ""));
    }
}
