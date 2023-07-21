package com.sprint.todo.SprintRestTodo.domain.user;

import com.sprint.todo.SprintRestTodo.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    Optional<User> findUserByEmail(String id);
    Page<User> findAllByActiveTrue(Pageable pageable);
}
