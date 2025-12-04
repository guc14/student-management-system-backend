# 🎓 Student Management System (Spring Boot + MySQL)

A clean, modular Student Management System backend API built with **Java 17**, **Spring Boot 3**, **Spring Data JPA**, and **MySQL**.

It demonstrates:

- Modern backend development practices (RESTful APIs, layered architecture, DTOs)
- JPA relationships (One-to-One, Many-to-Many via Enrollment)
- Global exception handling, validation, and logging with AOP

> Designed as a portfolio project for **junior backend developer** positions.

---

## 🧰 Tech Stack

- **Language**：Java 17（Zhōng-shì fāyīn: zhā-vǎ）
- **Framework**：Spring Boot 3, Spring MVC
- **Persistence**：Spring Data JPA, Hibernate, ORM
- **Database**：MySQL 8
- **Build Tool**：Maven
- **Other**：Lombok, Validation (`jakarta.validation`), AOP, Logging (SLF4J / Logback)

---

## 🗂 Project Structure

```text
com.example.demo
│
├── controller          # REST controllers (Student, Profile, Course, Enrollment)
├── service             # Business logic layer
├── repository          # Spring Data JPA repositories
│
├── model               # JPA entities
│   ├── Student
│   ├── StudentProfile
│   ├── Course
│   └── Enrollment
│
├── dto                 # DTOs for requests & responses
│   ├── StudentDto
│   ├── CreateStudentRequest
│   ├── UpdateStudentRequest
│   ├── StudentProfileDto
│   ├── CourseDto
│   └── EnrollmentInfoDto
│
├── mapper              # Mapper classes (Entity ↔ DTO)
│
├── exception           # Custom exceptions & GlobalExceptionHandler
│   ├── StudentNotFoundException
│   ├── CourseNotFoundException
│   ├── EnrollmentException
│   └── GlobalExceptionHandler
│
└── aop                 # Logging Aspect (method-level logging)

✨ Features
👩‍🎓 1. Student Management

Create a new student

Update existing student

Delete student

Get student by ID

Get all students (DTO formatted)

Input validation using @Valid and constraint annotations

Clean, wrapped JSON responses using ApiResponse<T>

🧾 2. Student Profile (1-to-1)

Each student has exactly one profile (StudentProfile)

Create profile for a student

Update profile

View a student's profile

Implemented with bi-directional @OneToOne JPA relationship:

Student ↔ StudentProfile

Foreign key stored in student_profiles table

📘 3. Course Management

Create course

Update course

Delete course

Get course by ID

Get all courses

DTO-based communication (CourseDto)

📝 4. Course Enrollment (Many-to-Many)

Student enrolls into a course

Prevent duplicate enrollment

Return structured info via EnrollmentInfoDto

Many-to-Many based on Enrollment join entity:

Student —< Enrollment >— Course

Example scenario:

Student(id=1) enrolls in Course(id=2) via
POST /courses/2/students/1

🚨 5. Global Exception Handling

Centralized exception handling using @RestControllerAdvice:

StudentNotFoundException

CourseNotFoundException

EnrollmentException (e.g., duplicate enrollment)

Validation errors (MethodArgumentNotValidException)

Generic fallback exceptions

All errors return a consistent JSON format (e.g. ExceptionResponse):

{
  "timestamp": "2025-11-29T08:48:05.1214987",
  "status": 404,
  "error": "Not Found",
  "message": "Student with id=999 does not exist",
  "path": "/students/999"
}

📦 6. Unified API Response Wrapper

All successful responses are wrapped in a generic ApiResponse<T>:

{
  "success": true,
  "data": {
    "id": 1,
    "name": "Anna",
    "age": 20
  }
}


Benefits:

Frontend can always rely on the same top-level fields

Easier error handling and logging

Cleaner, production-style API design

📋 7. Logging & AOP

Simple logging aspect in aop package

Log request handling for selected methods

Useful for debugging and tracing business logic flow

🧱 Database ER Diagram
+-------------------+        +----------------------+
|      Student      |        |   StudentProfile     |
+-------------------+        +----------------------+
| id (PK)           | 1   1  | id (PK)              |
| name              |--------| address / phone ...  |
| age               |        | student_id (FK)      |
+-------------------+        +----------------------+

          1                         *
        Student                Enrollment                 * 
+-------------------+      +-------------------+      +----------------+
|      Student      |  1  * |    Enrollment    | *  1 |     Course     |
+-------------------+      +-------------------+      +----------------+
| id (PK)           |      | id (PK)           |      | id (PK)        |
| ...               |      | student_id (FK)   |      | name           |
+-------------------+      | course_id (FK)    |      | ...            |
                           +-------------------+      +----------------+


Tables:

students

student_profiles

courses

enrollments

⚙️ Getting Started
1️⃣ Prerequisites

JDK 17+

Maven 3.x

MySQL 8.x

IDE (IntelliJ IDEA recommended)

2️⃣ Clone the Repository
git clone <(https://github.com/guc14/student-management-system-backend)>
cd student-management-system


3️⃣ Configure MySQL

Create a database:

CREATE DATABASE demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


Update src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=your-username
spring.datasource.password=your-password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

4️⃣ Build & Run

Using Maven:

mvn clean package
mvn spring-boot:run


Or run the DemoApplication main class from your IDE.

Server default port: http://localhost:8080

📡 API Endpoints (Overview)
👩‍🎓 Students
Method	Endpoint	Description
GET	/students	Get all students
GET	/students/{id}	Get student by ID
POST	/students	Create new student
PUT	/students/{id}	Update student
DELETE	/students/{id}	Delete student

Create Student – Request Body:

{
  "name": "Anna",
  "age": 20
}

🧾 Student Profile
Method	Endpoint	Description
POST	/students/{studentId}/profile	Create profile for student
GET	/students/{studentId}/profile	Get student's profile
PUT	/students/{studentId}/profile	Update student's profile

Example response (wrapped):

{
  "success": true,
  "data": {
    "studentId": 1,
    "fullName": "Anna",
    "phone": "123-456-7890"
  }
}

📘 Courses
Method	Endpoint	Description
GET	/courses	Get all courses
GET	/courses/{id}	Get course by ID
POST	/courses	Create course
PUT	/courses/{id}	Update course
DELETE	/courses/{id}	Delete course
📝 Enrollment
Method	Endpoint	Description
POST	/courses/{courseId}/students/{studentId}	Enroll student into a course
GET	/students/{studentId}/courses	Get courses of a student
GET	/courses/{courseId}/students	Get students of a course

Example success response:

{
  "success": true,
  "data": {
    "message": "Student 3 successfully enrolled into Course 2",
    "studentName": "Anna",
    "courseName": "Math 101"
  }
}

❌ Validation Error Example

If client sends invalid data:

{
  "name": "",
  "age": -1
}


Server returns:

{
  "timestamp": "2025-11-29T09:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "name must not be blank; age must be greater than 0",
  "path": "/students"
}

🚀 Future Improvements

Add pagination & sorting for list endpoints

Add search filters (by name, age, course name, etc.)

Add authentication / authorization (JWT)

Add Swagger / OpenAPI documentation

Add integration tests and unit tests for services

💬 About This Project

This project is part of my journey transitioning into Java backend development.
It focuses on:

Writing clean and maintainable code

Understanding real-world Spring Boot project structure

Practicing RESTful API design, JPA relationships, and error handling

欢迎联系我交流学习心得 😊
