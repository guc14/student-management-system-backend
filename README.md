🎓 Student Management System (Spring Boot + MySQL)

A clean, modular, production-style Student Management System API built with
Java 17, Spring Boot, Spring Data JPA, MySQL, and DTO + Exception Handler + Logging AOP.

This project is designed to demonstrate backend development skills suitable for junior backend developer positions.

🛠 Tech Stack

Java 17

Spring Boot 3

Spring Web

Spring Data JPA

MySQL

Maven

Lombok

AOP Logging

📦 Project Structure
com.example.demo
├── controller      # REST APIs
├── service         # Business Logic
├── repository      # JPA Repositories
├── model           # Entities (Student, Profile, Course, Enrollment)
├── dto             # Request/Response DTOs
├── mapper          # Convert Entity ↔ DTO
├── exception       # Global handlers & custom exceptions
└── aop             # Logging Aspect

✨ Features
👤 Student Management

Create student

Update student

Delete student

Get student by ID

Get all students (DTO formatted)

📝 Student Profile (1-to-1)

Create profile for a student

Update profile

Get profile

Includes phone, address, emergency contact

Linked through student_id

📚 Course Enrollment (Many-to-Many)

Student selects a course

Get a student's selected courses

Get all students of a course

Enrollment info includes selected date + course name

📄 Pagination + Sorting + Search (预留结构)

Standard PageResponse DTO

Clean JSON for frontend use

🛡 Global Exception Handling

Example error response:

{
  "status": 404,
  "message": "Student not found",
  "path": "/students/99"
}

📜 Logging AOP

Logs:

API endpoint

HTTP method

Execution time

Inputs & outputs

🔗 API Endpoints (Summary)
Student API
Method	Endpoint	Description
POST	/students	Create new student
GET	/students	Get all students
GET	/students/{id}	Get student by ID
PUT	/students/{id}	Update student
DELETE	/students/{id}	Delete student
Student Profile API
Method	Endpoint	Description
POST	/students/{id}/profile	Create profile
GET	/students/{id}/profile	Get profile
PUT	/students/{id}/profile	Update profile
Course API
Method	Endpoint	Description
POST	/courses/{courseId}/students/{studentId}	Student enrolls course
GET	/courses/by-student/{studentId}	Get courses of student
GET	/courses/{courseId}/students	Get students of a course
GET	/courses/enrollments/by-student/{studentId}	Enrollment info
🚀 Running the Project
1️⃣ Configure MySQL
CREATE DATABASE student_db;

2️⃣ Run the project
mvn spring-boot:run

🙌 Author

Chen — Spring Boot backend developer in training.

If you like this project, please ⭐ star the repo — it helps a lot!
