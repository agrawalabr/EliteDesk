# Spring Backend Application

This is a Spring Boot backend application with basic configuration and setup.

## Prerequisites

- Java 17 or higher
- Maven 3.6.x or higher

## Project Structure

```
spring-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── springbackend/
│   │   │               └── SpringBackendApplication.java
│   │   └── resources/
│   │       └── application.properties
└── pom.xml
```

## Features

- Spring Boot 3.2.3
- Spring Web (REST APIs)
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot Test

## Getting Started

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   or
   ```bash
   mvn spring-boot:run
   ```

## Available Endpoints

The application will start on port 8080. You can access:

- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:testdb
  - Username: sa
  - Password: (empty)

## Development

This project uses:

- Java 17
- Maven for dependency management
- H2 as an in-memory database
- Spring Data JPA for database operations
- Lombok for reducing boilerplate code

## Testing

Run tests using:

```bash
./mvnw test
```

or

```bash
mvn test
```
