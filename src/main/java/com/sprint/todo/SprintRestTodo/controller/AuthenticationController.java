package com.sprint.todo.SprintRestTodo.controller;

import com.sprint.todo.SprintRestTodo.domain.user.UserRepository;
import com.sprint.todo.SprintRestTodo.domain.user.dto.UserAuth;
import com.sprint.todo.SprintRestTodo.domain.user.dto.UserDataRegister;
import com.sprint.todo.SprintRestTodo.domain.user.model.User;
import com.sprint.todo.SprintRestTodo.infra.security.TokenService;
import com.sprint.todo.SprintRestTodo.infra.security.dto.JWTData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@Tag(name = "Authentication Controller", description = "Controller responsible for user authentication")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Login", description = "Log in with email and password")
    @ApiResponse(responseCode = "200", description = "Successfully logged in")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "401", description = "Unauthorized, credentials are wrong")
    @PostMapping("/login")
    public ResponseEntity<JWTData> login(@RequestBody @Valid UserAuth userAuth) {
        Authentication token = new UsernamePasswordAuthenticationToken(userAuth.email(), userAuth.password());
        var userAuthenticated = authenticationManager.authenticate(token);
        var user = (User) userAuthenticated.getPrincipal();
        return ResponseEntity.ok(new JWTData(tokenService.generateToken(user)));
    }

    @Operation(summary = "Sign up", description = "Create a new user and authenticate it")
    @ApiResponse(responseCode = "200", description = "Successfully created and authenticated the user")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @PostMapping("/signup")
    public ResponseEntity<JWTData> signup(@RequestBody @Valid UserDataRegister userDataRegister) {
        if(!userDataRegister.password().equals(userDataRegister.passwordConfirmation())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        if (repository.findUserByEmail(userDataRegister.email()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already in use");
        }
        User user = new User(userDataRegister);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        repository.save(user);

        Authentication token = new UsernamePasswordAuthenticationToken(
                userDataRegister.email(),
                userDataRegister.password());

        var userAuthenticated = authenticationManager.authenticate(token);
        var userResponse = (User) userAuthenticated.getPrincipal();
        return ResponseEntity.ok(new JWTData(tokenService.generateToken(userResponse)));
    }
}