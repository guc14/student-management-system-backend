🎓 Student Management System (Spring Boot + MySQL)

A clean, modular, production-style Student Management System API built with
Java 17, Spring Boot 3, Spring Data JPA, and MySQL 8,
and includes DTO, Exception Handling, Logging AOP, and JPA relationships.

This project is designed to demonstrate backend development skills suitable for junior backend developer positions.

🧰 Tech Stack

Java 17   |  
Spring Boot 3.x   |  
Spring Web MVC   |  
Spring Data JPA   |  
ORM   |  
MySQL 8.0   |  
Maven   |  
Build   |  
Lombok   |  
Validation   |  
AOP   |  
Logging

🗂 Project Structure
com.example.demo
│
├── controller      # REST APIs
├── service         # Business Logic
├── repository      # JPA Repositories
│
├── model           # Entities (Student, Profile, Course, Enrollment)
├── dto             # Request/Response DTOs
├── mapper          # Convert Entity ↔ DTO
│
├── exception       # Global handlers & custom exceptions
└── aop             # Logging Aspect

✨ Features
👩‍🎓 Student Management

Create student

Update student

Delete student

Get student by ID

Get all students (DTO formatted)

🧾 Student Profile (1-to-1)

Create profile for a student

Update profile

Get profile by student ID

One-to-one relationship: Student ↔ StudentProfile

📘 Course Management

Create course

Update course

Delete course

Get course by ID

List all courses

📝 Course Enrollment (Many-to-Many)

Student enrolls into a course

Prevent duplicate enrollment

Return structured enrollment info (EnrollmentInfoDto)

Implemented using middle table Enrollment

🚨 Global Exception Handling

StudentNotFoundException

CourseNotFoundException

EnrollmentException

Validation errors

Consistent JSON error response format

📦 Unified API Response Wrapper

All successful responses follow:

{
  "success": true,
  "data": { ... }
}

🧱 Database ER Diagram
Student 1 --- 1 StudentProfile

Student 1 --- * Enrollment * --- 1 Course

⚙️ Getting Started
1️⃣ Clone the Repository
git clone https://github.com/guc14/student-management-system-backend

2️⃣ Configure MySQL
CREATE DATABASE demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

3️⃣ Run the App
mvn spring-boot:run


App runs at:
👉 http://localhost:8080

📡 API Endpoints
Students

GET /students

GET /students/{id}

POST /students

PUT /students/{id}

DELETE /students/{id}

Student Profile

POST /students/{studentId}/profile

GET /students/{studentId}/profile

PUT /students/{studentId}/profile

Courses

GET /courses

GET /courses/{id}

POST /courses

PUT /courses/{id}

DELETE /courses/{id}

Enrollment

POST /courses/{courseId}/students/{studentId}

GET /students/{studentId}/courses

GET /courses/{courseId}/students

🚀 Future Improvements

Pagination & sorting

Search filters (name, age, course name)

JWT Authentication

Swagger / OpenAPI docs

Test cases

💬 About This Project

This project is part of my journey transitioning into Java backend development — focusing on clean code, real-world architecture, and REST API design.

欢迎交流学习心得 😊
