package com.sprint.todo.SprintRestTodo.domain.todo.model;

import com.sprint.todo.SprintRestTodo.domain.todo.dto.TodoDataDetails;
import com.sprint.todo.SprintRestTodo.domain.todo.dto.TodoDataRegister;
import com.sprint.todo.SprintRestTodo.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "todo")
@Entity(name = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean completed;
    @Column(name = "is_active")
    private boolean active;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Todo(TodoDataRegister data, User user) {
        this.title = data.title();
        this.description = data.description();
        this.completed = data.completed();
        this.active = true;
        this.user = user;
    }
    public TodoDataDetails toTodoDataDetails() {
        return new TodoDataDetails(
                this.id,
                this.title,
                this.description,
                this.completed,
                this.createdAt,
                this.updatedAt
        );
    }
    public void updateData(TodoDataDetails data) {
        if (this.title != null){
            this.title = data.title();
        }
        if (this.description != null){
            this.description = data.description();
        }
        if (this.completed != data.completed()){
            this.completed = data.completed();
        }
    }
    public void delete() {
        this.active = false;
    }

}
