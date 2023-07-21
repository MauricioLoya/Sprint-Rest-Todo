package com.sprint.todo.SprintRestTodo.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Sprint Rest Todo",
                version = "1.0",
                description = "Sprint Rest Todo API",
                contact = @Contact(
                        name = "Mauricio Loya",
                        email = "mauloy15@gmail.com",
                        url = "www.mauricioloya.com"
                )
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "Bearer Token Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
