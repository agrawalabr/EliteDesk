# 🏢 EliteDesk - Modern Workspace Management System (Backend)

<div align="left">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-brightgreen) ![Java](https://img.shields.io/badge/Java-17-orange) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue) ![JWT](https://img.shields.io/badge/JWT-Authentication-yellow) ![Status](https://img.shields.io/badge/Status-Developed-brightgreen)

</div>

## 📋 Overview

EliteDesk is a sophisticated workspace management system built with Spring Boot, designed to streamline the process of booking and managing various types of workspaces. The backend provides comprehensive APIs for managing workspaces, reservations, and user authentication with a focus on security and scalability.

## ✨ Features

### 🔐 Authentication & Security
- Secure login and registration system ([`UserController.java`](src/main/java/com/example/springbackend/controller/UserController.java))
- Role-based access control ([`SecurityConfig.java`](src/main/java/com/example/springbackend/security/SecurityConfig.java))
- Session management with JWT tokens ([`JwtService.java`](src/main/java/com/example/springbackend/security/JwtService.java))
- User profile management ([`User.java`](src/main/java/com/example/springbackend/model/User.java))

### 🏢 Space Management
- Multiple space types support ([`SpaceType.java`](src/main/java/com/example/springbackend/model/SpaceType.java)):
  - Conference Rooms
  - Meeting Rooms
  - Offices
  - Coworking Spaces
  - Event Spaces
  - Studios
- Detailed space information ([`Space.java`](src/main/java/com/example/springbackend/model/Space.java)):
  - Capacity
  - Location
  - Pricing
  - Availability status

### 📅 Reservation System
- Real-time availability checking ([`ReservationService.java`](src/main/java/com/example/springbackend/service/ReservationService.java))
- Time slot management ([`TimeSlot.java`](src/main/java/com/example/springbackend/dto/TimeSlot.java))
- Reservation management ([`Reservation.java`](src/main/java/com/example/springbackend/model/Reservation.java)):
  - Create new reservations
  - Modify existing bookings
  - Cancel reservations
  - View booking history

### 🛡️ Error Handling & Validation
- Global exception handling ([`GlobalExceptionHandler.java`](src/main/java/com/example/springbackend/exception/GlobalExceptionHandler.java))
- Input validation
- Custom error responses
- Detailed error logging
- User-friendly error messages

## 🏗️ Architecture

### Backend
- **Spring Boot 3.2.3**: Core framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database operations
- **PostgreSQL**: Database
- **JWT**: Token-based authentication

### Core Components

#### Controllers
- [`HealthController.java`](src/main/java/com/example/springbackend/controller/HealthController.java): System health monitoring
- [`ReservationController.java`](src/main/java/com/example/springbackend/controller/ReservationController.java): Reservation management
- [`SpaceController.java`](src/main/java/com/example/springbackend/controller/SpaceController.java): Space operations
- [`UserController.java`](src/main/java/com/example/springbackend/controller/UserController.java): User authentication

#### Services
- [`AuthenticationService.java`](src/main/java/com/example/springbackend/service/AuthenticationService.java): JWT-based authentication
- [`ReservationService.java`](src/main/java/com/example/springbackend/service/ReservationService.java): Reservation business logic
- [`SpaceService.java`](src/main/java/com/example/springbackend/service/SpaceService.java): Space management
- [`UserService.java`](src/main/java/com/example/springbackend/service/UserService.java): User operations

#### Models
- [`User.java`](src/main/java/com/example/springbackend/model/User.java): User entity
- [`Space.java`](src/main/java/com/example/springbackend/model/Space.java): Space entity
- [`Reservation.java`](src/main/java/com/example/springbackend/model/Reservation.java): Reservation entity
- [`ReservationStatus.java`](src/main/java/com/example/springbackend/model/ReservationStatus.java): Enum for reservation states
- [`SpaceType.java`](src/main/java/com/example/springbackend/model/SpaceType.java): Enum for space categories
- [`Role.java`](src/main/java/com/example/springbackend/model/Role.java): User role enumeration

#### Security
- [`JwtAuthenticationFilter.java`](src/main/java/com/example/springbackend/security/JwtAuthenticationFilter.java): JWT token validation
- [`JwtService.java`](src/main/java/com/example/springbackend/security/JwtService.java): Token generation and validation
- [`SecurityConfig.java`](src/main/java/com/example/springbackend/security/SecurityConfig.java): Security configuration
- [`CustomUserDetails.java`](src/main/java/com/example/springbackend/security/CustomUserDetails.java): User details implementation

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6.x or higher
- PostgreSQL 14 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/EliteDesk.git
```

2. Configure the database:
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springdb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register`: User registration
- `POST /api/auth/login`: User login

### Spaces
- `GET /api/spaces`: List all spaces
- `GET /api/spaces/{id}`: Get space details
- `POST /api/spaces`: Create new space (Admin only)

### Reservations
- `POST /api/reservations`: Create reservation
- `POST /api/reservations/email`: Create reservation with email
- `POST /api/reservations/{id}/cancel`: Cancel reservation
- `GET /api/reservations/space/{spaceId}`: Get space reservations
- `GET /api/reservations/user/{userId}`: Get user reservations
- `GET /api/reservations/availability`: Check space availability

### Health
- `GET /api/health`: System health check

## 📱 Application Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── springbackend/
│   │               ├── config/         # Configuration classes
│   │               │   └── SecurityConfig.java
│   │               ├── controller/     # REST controllers
│   │               │   ├── HealthController.java
│   │               │   ├── ReservationController.java
│   │               │   ├── SpaceController.java
│   │               │   └── UserController.java
│   │               ├── dto/            # Data Transfer Objects
│   │               │   ├── ReservationDTO.java
│   │               │   ├── SpaceDTO.java
│   │               │   ├── TimeSlot.java
│   │               │   └── UserDTO.java
│   │               ├── exception/      # Custom exceptions
│   │               │   ├── GlobalExceptionHandler.java
│   │               │   └── ResourceNotFoundException.java
│   │               ├── model/          # Entity models
│   │               │   ├── Reservation.java
│   │               │   ├── ReservationStatus.java
│   │               │   ├── Role.java
│   │               │   ├── Space.java
│   │               │   ├── SpaceType.java
│   │               │   └── User.java
│   │               ├── repository/     # Data access layer
│   │               │   ├── ReservationRepository.java
│   │               │   ├── SpaceRepository.java
│   │               │   └── UserRepository.java
│   │               ├── security/       # Security components
│   │               │   ├── CustomUserDetails.java
│   │               │   ├── JwtAuthenticationFilter.java
│   │               │   └── JwtService.java
│   │               └── service/        # Business logic
│   │                   ├── AuthenticationService.java
│   │                   ├── ReservationService.java
│   │                   ├── SpaceService.java
│   │                   └── UserService.java
│   └── resources/
│       └── application.properties      # Application configuration
```

## 🔧 Configuration

### Application Properties
```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/springdb
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=your-secret-key
jwt.expiration=86400
```

## 🔐 Security

- JWT-based authentication
- Role-based access control
- Password encryption
- Input validation
- CORS configuration
- Secure session management

## 📈 Future Enhancements

- [ ] Mobile application
- [ ] Real-time notifications
- [ ] Analytics dashboard
- [ ] Integration with calendar services
- [ ] Payment gateway integration

## 👥 Authors

- **Abhishek Agrawal** - *Developer* - [![GitHub](https://img.shields.io/badge/GitHub-Profile-informational?logo=github)](https://github.com/agrawalabr) [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?logo=linkedin)](https://www.linkedin.com/in/agrawalabr)
- **Maneesh Kolli** - *Developer* - [![GitHub](https://img.shields.io/badge/GitHub-Profile-informational?logo=github)](https://github.com/Maneeshk11) [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?logo=linkedin)](https://www.linkedin.com/in/maneeshkolli)

## 📚 References

- Spring Boot Documentation
- JWT Documentation
- PostgreSQL Documentation
- Spring Security Documentation

---

<div align="center">
Made with ❤️ by EliteDesk Team
</div>
