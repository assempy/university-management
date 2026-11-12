# University Management System

## Overview

A Spring Boot REST API application that manages students, courses, and grades using three independent PostgreSQL databases.

The application follows a layered architecture with separate configuration, entity, repository, service, and controller layers.

## Technologies

- Java 17
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- PostgreSQL 18
- Maven
- REST API
- Postman

## Database Architecture

The application uses three independent PostgreSQL databases:

1. **student_db** – stores student information.
2. **course_db** – stores course information.
3. **grade_db** – stores student grades.

Each database has its own:

- DataSource
- EntityManagerFactory
- TransactionManager

The `grade_db` database stores `student_id` and `course_id` as logical references. There are no foreign keys between databases.

Before creating a grade, the application checks whether the referenced student and course exist in their respective databases.

### Database Tables

**student_db**
- `students`

**course_db**
- `courses`

**grade_db**
- `grades`

SQL scripts for creating the database tables are located in `src/main/resources/sql/`:

- `student_db.sql`
- `course_db.sql`
- `grade_db.sql`

## Project Structure

```text
university-management/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/university/universitymanagement/
│       │       ├── config/
│       │       ├── student/
│       │       │   ├── entity/
│       │       │   ├── repository/
│       │       │   ├── service/
│       │       │   └── controller/
│       │       ├── course/
│       │       │   ├── entity/
│       │       │   ├── repository/
│       │       │   ├── service/
│       │       │   └── controller/
│       │       ├── grade/
│       │       │   ├── entity/
│       │       │   ├── repository/
│       │       │   ├── service/
│       │       │   └── controller/
│       │       ├── dto/
│       │       ├── exception/
│       │       └── UniversityManagementApplication.java
│       └── resources/
│           ├── application.properties
│           └── sql/
│               ├── student_db.sql
│               ├── course_db.sql
│               └── grade_db.sql
├── postman/
│   └── collections/
│       └── University Management API/
├── pom.xml
└── README.md
```

## API Endpoints

### Students

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |
| GET | `/api/students/{id}/transcript` | Get student transcript |

### Courses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/courses` | Get all courses |
| GET | `/api/courses/{id}` | Get course by ID |
| POST | `/api/courses` | Create course |
| PUT | `/api/courses/{id}` | Update course |
| DELETE | `/api/courses/{id}` | Delete course |

### Grades

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/grades` | Create grade |
| GET | `/api/grades/student/{id}` | Get grades by student ID |

## Running the Application

### Prerequisites

- Java 17
- PostgreSQL 18
- IntelliJ IDEA or another Java IDE

### 1. Create Databases

Create the following databases in PostgreSQL:

- `student_db`
- `course_db`
- `grade_db`

Execute each SQL script from `src/main/resources/sql/` in its corresponding database using pgAdmin or another PostgreSQL client.

### 2. Configure Database Credentials

Database connection settings are defined in `src/main/resources/application.properties`.

The database password is read from the `DB_PASSWORD` environment variable:

```properties
app.datasource.student.password=${DB_PASSWORD}
app.datasource.course.password=${DB_PASSWORD}
app.datasource.grade.password=${DB_PASSWORD}
```

Set `DB_PASSWORD` to your PostgreSQL password.

In IntelliJ IDEA:

1. Open **Run → Edit Configurations**.
2. Select `UniversityManagementApplication`.
3. Find **Environment variables**.
4. Add `DB_PASSWORD=your_postgresql_password`.
5. Apply the changes.

Do not store actual database passwords in the source code or commit them to version control.

### 3. Run the Application

Run the `UniversityManagementApplication` main class.

The application will start at:

`http://localhost:8080`

## Validation and Error Handling

The application handles the following HTTP status codes:

- **400 Bad Request** – invalid input data or validation errors.
- **404 Not Found** – student or course does not exist.
- **409 Conflict** – duplicate email, duplicate course code, or data integrity conflict.
- **500 Internal Server Error** – unexpected server errors.

Grade scores must be between 0 and 100.

## Testing

API endpoints were tested using Postman.

The Postman collection is stored in:

`postman/collections/University Management API/`

Testing covers:

- Student CRUD operations.
- Course CRUD operations.
- Grade creation and retrieval.
- Student transcript generation.
- Input validation.
- Duplicate student emails.
- Missing student and course references.

