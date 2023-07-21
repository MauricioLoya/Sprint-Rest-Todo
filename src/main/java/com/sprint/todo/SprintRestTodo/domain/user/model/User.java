package com.sprint.todo.SprintRestTodo.domain.user.model;

import com.sprint.todo.SprintRestTodo.domain.todo.model.Todo;
import com.sprint.todo.SprintRestTodo.domain.user.dto.UserDataDetails;
import com.sprint.todo.SprintRestTodo.domain.user.dto.UserDataRegister;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    @Column(name = "is_active")
    private boolean active;

    @OneToMany(mappedBy = "user")
    private Set<Todo> todos;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public User(UserDataRegister userDataRegister) {
        if(userDataRegister.password().equals(userDataRegister.passwordConfirmation())) {
            this.email = userDataRegister.email();
            this.name = userDataRegister.name();
            this.lastName = userDataRegister.lastName();
            this.password = userDataRegister.password();
            this.active = true;
        }
    }
    public void updateData(UserDataDetails userDataDetails) {
        if (this.name != null){
            this.name = userDataDetails.name();
        }
        if (this.lastName != null){
            this.lastName = userDataDetails.lastName();
        }
        if (this.email != null){
            this.email = userDataDetails.email();
        }
    }
    public void delete() {
        this.active = false;
    }

    public UserDataDetails toUserDataDetails() {
        return new UserDataDetails(
                this.email,
                this.name,
                this.lastName,
                this.isActive()
        );
    }

}