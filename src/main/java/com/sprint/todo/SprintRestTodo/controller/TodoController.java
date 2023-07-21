package com.sprint.todo.SprintRestTodo.controller;

import com.sprint.todo.SprintRestTodo.domain.todo.TodoRepository;
import com.sprint.todo.SprintRestTodo.domain.todo.dto.TodoDataDetails;
import com.sprint.todo.SprintRestTodo.domain.todo.dto.TodoDataList;
import com.sprint.todo.SprintRestTodo.domain.todo.dto.TodoDataRegister;
import com.sprint.todo.SprintRestTodo.domain.todo.model.Todo;
import com.sprint.todo.SprintRestTodo.domain.user.UserRepository;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@Tag(name = "Todo Controller", description = "Controller responsible for user's todos operations")
@RequestMapping("user/todo")
public class TodoController {

    @Autowired
    private TodoRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Create a todo", description = "Create a new todo for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Successfully created the todo")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @PostMapping
    public ResponseEntity<TodoDataDetails> registerTodo(
            @RequestBody @Valid TodoDataRegister data,
            UriComponentsBuilder uriBuilder,
            @RequestHeader("Authorization") String token
    ) {
        Long userId = getUserId(token);
        User user = userRepository.findById(userId).orElseThrow();
        Todo todo = repository.save(new Todo(data, user));
        URI uri = uriBuilder.path("/user/todo/{id}").buildAndExpand(todo.getId()).toUri();
        return ResponseEntity.created(uri).body(todo.toTodoDataDetails());

    }

    @Operation(summary = "Get all todos", description = "Get all todos for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of todos")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @GetMapping
    public ResponseEntity<Page<TodoDataList>> getAllTodos(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable,
            @RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        return ResponseEntity.ok(repository.findAllByUserIdAndActiveTrue(pageable,userId).map(TodoDataList::new));
    }

    @Operation(summary = "Get a todo", description = "Get a specific todo by id for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the todo")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDataDetails> getOneUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);

        Todo todo = repository.findByUserIdAndIdAndActiveTrue(userId,id).orElseThrow();
        return ResponseEntity.ok(todo.toTodoDataDetails());
    }

    @Operation(summary = "Update a todo", description = "Update a specific todo by id for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Successfully updated the todo")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TodoDataDetails> update(
            @PathVariable Long id,
            @RequestBody @Valid TodoDataDetails data,
            @RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        Todo todo = repository.findByUserIdAndIdAndActiveTrue(userId,id).orElseThrow();

        todo.updateData(data);
        repository.save(todo);
        return ResponseEntity.ok(todo.toTodoDataDetails());

    }

    @Operation(summary = "Delete a todo", description = "Delete a specific todo by id for the authenticated user")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the todo")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        Long userId = getUserId(token);
        Todo todo = repository.findByUserIdAndIdAndActiveTrue(userId,id).orElseThrow();
        todo.delete();
        return ResponseEntity.noContent().build();
    }

    private Long getUserId(String token) {
        return tokenService.getUserId(token.replace("Bearer ", ""));
    }
}
