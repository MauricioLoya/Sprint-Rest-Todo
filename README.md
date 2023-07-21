# SprintRestTodo - Spring Boot CRUD Project with Authentication

SprintRestTodo is a Spring Boot project that serves as a starter template for building a web application with CRUD operations for tasks and users, along with an authentication module (sign in and sign up). This project is designed to help you quickly get started with building your own task management application with user authentication using Spring Boot.

## Prerequisites

Before you begin, make sure you have the following installed:

- Java 17 (JDK 17)
- MySQL (or any other compatible relational database)
- Your favorite Java IDE (e.g., IntelliJ, Eclipse, VS Code, etc.)

## Getting Started

1. Clone the repository or download the project as a ZIP file.

2. Ensure you have a MySQL database set up. Update the `application.properties` file (or `application.yml` if you prefer YAML) located in `src/main/resources/` with your database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
```

3. Build the project using Maven. If you are using an IDE, you can use its built-in Maven integration, or you can run the following command in the project root:

```bash
mvn clean install
```

4. Run the application:

```bash
mvn spring-boot:run
```

The Spring Boot application will start, and you can access it at `http://localhost:8080`.

## API Documentation

This project integrates SpringDoc with OpenAPI to provide API documentation. You can access the Swagger UI to explore the endpoints and their details by visiting the following URL:

`http://localhost:8080/swagger-ui/index.html`

## Features

The SprintRestTodo project comes with the following features:

1. **Task CRUD**: The project provides endpoints for managing tasks, including creating, reading, updating, and deleting tasks.

2. **User CRUD**: You can manage users through endpoints that allow creating, reading, updating, and deleting user information.

3. **Authentication**: The project includes a basic authentication module with endpoints for user sign up and sign in.

4. **Database Migrations**: The project utilizes Flyway for database migrations, making it easy to set up and manage your database schema.

5. **Validation**: Input data validation is handled using Spring Boot's built-in validation.

6. **Security**: Spring Security is integrated to secure the endpoints and handle user authentication.

## Dependencies

The project uses the following major dependencies:

- Spring Boot 3.1.2
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Security
- Lombok
- Flyway Core and Flyway MySQL for database migrations
- MySQL Connector for database connectivity
- Auth0 Java JWT for JSON Web Token (JWT) handling
- SpringDoc OpenAPI Starter WebMvc UI for API documentation

## Contributing

If you'd like to contribute to this project or have any suggestions, feel free to create a pull request or raise an issue.

## Acknowledgments

Thank you for using SprintRestTodo as a starting point for your project. Happy coding!
