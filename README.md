# sample-crud-kotlin-app

A Spring Boot-based RESTful API for managing todo items. This application provides CRUD operations for todos, supports filtering by status, and includes database migrations using Liquibase.

---

## Table of Contents
1. [Architecture](#architecture)
2. [Technologies](#technologies)
3. [Setup](#setup)
4. [Running the Application](#running-the-application)
5. [API Endpoints](#api-endpoints)
6. [Testing](#testing)
7. [Database Migrations](#database-migrations)
8. [Contributing](#contributing)
9. [License](#license)

---

## Architecture

The application follows a layered architecture:
1. **Controller Layer**: Handles HTTP requests and responses (`TodoController`).
2. **Service Layer**: Contains business logic (`TodoService`).
3. **Repository Layer**: Manages database operations (`TodoRepository`).
4. **Entity Layer**: Represents the data model (`Todo`).
5. **Database Layer**: Uses H2 (in-memory) for development and PostgreSQL for production.
6. **Liquibase**: Manages database schema migrations.

---

## Technologies
- **Spring Boot 3.4.3**
- **Kotlin 2.1.0**
- **Spring Data JPA**
- **Liquibase**
- **H2 Database** (development)
- **PostgreSQL** (production)
- **Spring Boot Actuator**
- **JUnit 5**

---

## Setup

### Prerequisites
- Java 23
- Gradle 8+
- Docker (optional for PostgreSQL)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/todo-app.git
   cd todo-app
   ```
2. Build the project:

```bash
./gradlew build
```
3. Configure database in application.yml (optional for PostgreSQL):
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/todo_db
    username: postgres
    password: postgres
```
