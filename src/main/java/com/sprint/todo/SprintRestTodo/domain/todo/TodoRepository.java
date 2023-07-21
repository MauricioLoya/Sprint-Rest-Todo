package com.sprint.todo.SprintRestTodo.domain.todo;

import com.sprint.todo.SprintRestTodo.domain.todo.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Page<Todo> findAllByUserIdAndActiveTrue(Pageable pageable, Long userId);
    Optional<Todo> findByUserIdAndIdAndActiveTrue(Long userId, Long id);
}
