package com.sprint.todo.SprintRestTodo.infra.erros;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity errorHandler404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity errorHandler400(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors().stream().map(ErrorDataValidation::new).toList();
        return ResponseEntity.badRequest()
                .body(errors);
    }


    private record ErrorDataValidation(String field, String message) {
        public ErrorDataValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.badRequest().body('{' +
                "\"error\": \"" + e.getReason() + '"' +
                '}');
    }

}
