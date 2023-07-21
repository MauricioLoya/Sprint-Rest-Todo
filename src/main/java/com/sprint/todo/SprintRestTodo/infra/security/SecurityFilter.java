package com.sprint.todo.SprintRestTodo.infra.security;

import com.sprint.todo.SprintRestTodo.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.Console;
import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //get the token from the request header
        Optional<String> jwt = Optional.ofNullable(request.getHeader("Authorization"));
        if (jwt.isPresent()) {

            var token = jwt.get().replace("Bearer ", "");
            var subject = tokenService.getSubject(token);
            System.out.println(subject);
            if (subject != null) {
               var user = userRepository.findByEmail(subject);
               var authentication = new UsernamePasswordAuthenticationToken(user, user.getAuthorities(), user.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            // set the user as anonymous
        }
        filterChain.doFilter(request, response);





    }
}
