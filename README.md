🎓 Student Management System (Spring Boot + MySQL)

A clean, modular, production-style Student Management System API built with
Java 17, Spring Boot 3, Spring Data JPA, and MySQL 8.

This project demonstrates:
# 🎓 Student Management System (Spring Boot + MySQL)

A clean, modular, production-style Student Management System API built with **Java 17**, **Spring Boot 3**, **Spring Data JPA**, and **MySQL 8**.

This project demonstrates:

- Modern backend development practices (RESTful APIs, layered architecture, DTOs)
- JPA relationships (One-to-One, Many-to-Many via Enrollment)
- Global exception handling, validation, unified API responses
- Logging with AOP

> Designed as a portfolio project for **junior backend developer** positions.

---

## 🧰 Tech Stack

- **Language**: Java 17 （Zhōng-shì fāyīn: zhā-vǎ）
- **Framework**: Spring Boot 3, Spring MVC
- **Persistence**: Spring Data JPA, Hibernate, ORM
- **Database**: MySQL 8
- **Build Tool**: Maven
- **Other**: Lombok, Validation (jakarta.validation), AOP, Logging (SLF4J / Logback)

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
├── dto                 # Request & Response DTOs
│   ├── StudentDto
│   ├── CreateStudentRequest
│   ├── UpdateStudentRequest
│   ├── StudentProfileDto
│   ├── CourseDto
│   └── EnrollmentInfoDto
│
├── mapper              # Convert Entity ↔ DTO
│
├── exception           # Custom exceptions & GlobalExceptionHandler
│
└── aop                 # Logging Aspect
✨ Features
👩‍🎓 1. Student Management
Create a new student

Update existing student

Delete student

Get student by ID

Get all students (DTO formatted)

Validation using @Valid

Unified JSON success wrapper: ApiResponse<T>

🧾 2. Student Profile (1-to-1)
Each student has exactly one StudentProfile

Create / update / view profile

Bi-directional @OneToOne relationship: Student ↔ StudentProfile

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

Many-to-Many modeled with a join entity: Enrollment

Example:

h
Copy code
POST /courses/{courseId}/students/{studentId}
🚨 5. Global Exception Handling
Centralized exception handling using @RestControllerAdvice:

StudentNotFoundException

CourseNotFoundException

EnrollmentException

Validation errors (MethodArgumentNotValidException)

Generic fallback exception

Example error response:

json
Copy code
{
  "timestamp": "2025-11-29T08:48:05.1214987",
  "status": 404,
  "error": "Not Found",
  "message": "Student with id=999 does not exist",
  "path": "/students/999"
}
📦 6. Unified API Response Wrapper
Success responses are wrapped in:

json
Copy code
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

Logs selected method calls

Helps debugging and tracing request flow

🧱 Database ER Diagram
text
Copy code
+-------------------+        +----------------------+
|      Student      |        |   StudentProfile     |
+-------------------+        +----------------------+
| id (PK)           | 1   1  | id (PK)              |
| name              |--------| phone / address ...  |
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

IntelliJ IDEA (recommended)

2️⃣ Clone the Repository
bash
Copy code
git clone https://github.com/guc14/student-management-system-backend
cd student-management-system-backend
3️⃣ Configure MySQL
Create database:

sql
Copy code
CREATE DATABASE demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
Update src/main/resources/application.properties:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=your-username
spring.datasource.password=your-password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
4️⃣ Build & Run
bash
Copy code
mvn clean package
mvn spring-boot:run
Or run DemoApplication in your IDE.

App runs at: http://localhost:8080

📡 API Endpoints (Overview)
👩‍🎓 Students
GET /students – Get all students

GET /students/{id} – Get student by ID

POST /students – Create student

PUT /students/{id} – Update student

DELETE /students/{id} – Delete student

Example request:

json
Copy code
{
  "name": "Anna",
  "age": 20
}
🧾 Student Profile
POST /students/{studentId}/profile – Create profile

GET /students/{studentId}/profile – Get profile

PUT /students/{studentId}/profile – Update profile

Example response:

json
Copy code
{
  "success": true,
  "data": {
    "studentId": 1,
    "fullName": "Anna",
    "phone": "123-456-7890"
  }
}
📘 Courses
GET /courses – Get all courses

GET /courses/{id} – Get course by ID

POST /courses – Create course

PUT /courses/{id} – Update course

DELETE /courses/{id} – Delete course

📝 Enrollment
POST /courses/{courseId}/students/{studentId} – Enroll student

GET /students/{studentId}/courses – Get a student's courses

GET /courses/{courseId}/students – Get students in a course

Example success response:

json
Copy code
{
  "success": true,
  "data": {
    "message": "Student 3 successfully enrolled into Course 2",
    "studentName": "Anna",
    "courseName": "Math 101"
  }
}
❌ Validation Error Example
Request:

json
Copy code
{
  "name": "",
  "age": -1
}
Response:

json
Copy code
{
  "timestamp": "2025-11-29T09:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "name must not be blank; age must be greater than 0",
  "path": "/students"
}
🚀 Future Improvements
Pagination & sorting for list endpoints

Search filters (by name, age, course name, etc.)

Authentication / authorization (JWT)

Swagger / OpenAPI documentation

Integration tests & unit tests

💬 About This Project
This project is part of my journey transitioning into Java backend development.

It focuses on:

Writing clean and maintainable code

Understanding real-world Spring Boot project structure

Practicing RESTful API design, JPA relationships, and error handling

欢迎联系我交流学习心得 😊
Modern backend development practices (RESTful APIs, layered architecture, DTOs)

JPA relationships (One-to-One, Many-to-Many via Enrollment)

Global exception handling, validation, unified API responses

Logging with AOP

Designed as a portfolio project for junior backend developer positions.

🧰 Tech Stack
Category	Technology
Language	Java 17（Zhōng-shì fāyīn: zhā-vǎ）
Framework	Spring Boot 3, Spring MVC
Persistence	Spring Data JPA, Hibernate, ORM
Database	MySQL 8
Build Tool	Maven
Other	Lombok, Validation, AOP, Logging (SLF4J / Logback)
🗂 Project Structure
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
├── dto                 # Request & Response DTOs
│   ├── StudentDto
│   ├── CreateStudentRequest
│   ├── UpdateStudentRequest
│   ├── StudentProfileDto
│   ├── CourseDto
│   └── EnrollmentInfoDto
│
├── mapper              # Convert Entity ↔ DTO
│
├── exception           # Custom exceptions & GlobalExceptionHandler
│
└── aop                 # Logging Aspect

✨ Features
👩‍🎓 Student Management

Create student

Update student

Delete student

Get student by ID

Get all students (DTO formatted)

Validation using @Valid

Unified success response: ApiResponse<T>

🧾 Student Profile (1-to-1)

One student ↔ one profile

Create profile

Update profile

Fetch profile

Implemented using bi-directional @OneToOne

Foreign key stored in student_profiles table

📘 Course Management

Create course

Update course

Delete course

Get course by ID

Get all courses

DTO-based communication (CourseDto)

📝 Course Enrollment (Many-to-Many)

Enroll student into a course

Prevent duplicate enrollment

Detailed response using EnrollmentInfoDto

Implemented using Enrollment join entity:

Student  —<  Enrollment  >—  Course


Example API:
POST /courses/{courseId}/students/{studentId}

🚨 Global Exception Handling

Centralized exception handling with:

StudentNotFoundException

CourseNotFoundException

EnrollmentException

Validation errors (MethodArgumentNotValidException)

Generic fallback exception

Example error response:

{
  "timestamp": "2025-11-29T08:48:05.1214987",
  "status": 404,
  "error": "Not Found",
  "message": "Student with id=999 does not exist",
  "path": "/students/999"
}

📦 Unified API Response Wrapper

All success responses follow:

{
  "success": true,
  "data": {
    "id": 1,
    "name": "Anna",
    "age": 20
  }
}


Benefits:

Consistent frontend handling

Cleaner API

Easier logging & debugging

📋 Logging & AOP

Logging Aspect in /aop

Logs method execution for improved tracing

Helps track controller → service → repository flow

🧱 Database ER Diagram
+-------------------+        +----------------------+
|      Student      |        |   StudentProfile     |
+-------------------+        +----------------------+
| id (PK)           | 1   1  | id (PK)              |
| name              |--------| phone / address ...  |
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

IntelliJ IDEA (recommended)

2️⃣ Clone the Repository
git clone https://github.com/guc14/student-management-system-backend
cd student-management-system-backend

3️⃣ Configure MySQL

Create database:

CREATE DATABASE demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=your-username
spring.datasource.password=your-password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

4️⃣ Build & Run
mvn clean package
mvn spring-boot:run


Or run DemoApplication in IDE.

App runs at:
👉 http://localhost:8080

📡 API Endpoints Overview
👩‍🎓 Students
Method	Endpoint	Description
GET	/students	Get all students
GET	/students/{id}	Get student by ID
POST	/students	Create student
PUT	/students/{id}	Update student
DELETE	/students/{id}	Delete student

Sample Request:

{
  "name": "Anna",
  "age": 20
}

🧾 Student Profile
Method	Endpoint	Description
POST	/students/{studentId}/profile	Create profile
GET	/students/{studentId}/profile	Get profile
PUT	/students/{studentId}/profile	Update profile

Response Example:

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
GET	/courses	List all courses
GET	/courses/{id}	Get course
POST	/courses	Create
PUT	/courses/{id}	Update
DELETE	/courses/{id}	Delete
📝 Enrollment
Method	Endpoint	Description
POST	/courses/{courseId}/students/{studentId}	Enroll student
GET	/students/{studentId}/courses	Student enrolled courses
GET	/courses/{courseId}/students	Students in a course

Success Response:

{
  "success": true,
  "data": {
    "message": "Student 3 successfully enrolled into Course 2",
    "studentName": "Anna",
    "courseName": "Math 101"
  }
}

❌ Validation Error Example
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

Pagination & sorting

Search (name, age, course, etc.)

JWT Authentication

Swagger documentation

Unit & integration tests

💬 About This Project

This project is part of my journey transitioning into Java backend development.

It focuses on:

Clean & maintainable code

Real-world Spring Boot structure

REST API design

JPA relationship modeling

Exception handling & validation

欢迎交流学习心得 😊
