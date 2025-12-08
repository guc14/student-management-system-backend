🎓 Student Management System (Spring Boot + MySQL + Swagger)

A clean, modular backend system built with Java 17, Spring Boot 3, Spring Data JPA, and MySQL.

This project demonstrates real-world backend development skills: RESTful APIs, DTO pattern, one-to-one & many-to-many relationships, pagination, search filters, validation, and fully interactive Swagger / OpenAPI documentation.

Designed as a portfolio project for Java backend developer roles.

🧰 Tech Stack

Language: Java 17

Framework: Spring Boot 3, Spring MVC

Persistence: Spring Data JPA (Hibernate), ORM

Database: MySQL 8

API Docs: Swagger / OpenAPI (springdoc-openapi)

Tools: Maven, Lombok, Postman / Swagger UI

Architecture: Controller → Service → Repository → Entity

🏗 System Overview

This system manages:

Students

Courses

Student Profile (One-to-One)

Enrollments (Many-to-Many)

🔶 Features
Student Management

CRUD

Pagination and sorting

Keyword + age range search

Student Profile (1:1)

Create / Update / Delete

GET /students/{id}/profile

Course Management

CRUD

Get students in a course

Get courses taken by a student

Enrollment System (M:N)

Enroll student into course

Query by student

Query by course

Return combined EnrollmentInfo DTO

Swagger Documentation

Fully documented APIs

Grouped by modules (Student / Course / Student Profile)

Summary and description for each endpoint

“Try It Out” support in browser

🗂 Project Structure

src/main/java/com/example/demo
└── controller
└── dto
└── model
└── repository
└── service
└── exception

🧩 Architecture Diagram

Controller → Service → Repository → MySQL

🗄 ER Diagram

Student (1) —— (1) StudentProfile

Student (M) —— (M) Course
via Enrollment (middle table)

📚 API Documentation (Swagger UI)

Swagger URL (local):
http://localhost:8080/swagger-ui/index.html

🚀 How to Run

Clone project

git clone https://github.com/your-username/student-management-system.git

cd student-management-system

Create MySQL database

CREATE DATABASE student_db;

Configure application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

Run the application

在 IntelliJ IDEA 中：

打开 DemoApplication

点击 Run ▶

或者在命令行执行：
mvn spring-boot:run

🔍 Key Technical Highlights

DTO pattern to separate API models from entities

One-to-One relationship: Student ↔ StudentProfile

Many-to-Many via Enrollment middle table

Global exception handling with @RestControllerAdvice

Pagination using Page and Pageable

Search filters (keyword + age range)

Swagger / OpenAPI documentation with annotations (@Tag, @Operation, @Parameter)

🔮 Future Enhancements

JWT authentication and authorization

Docker support

Deploy to cloud platform

More modules (attendance, scheduling, etc.)

A complete backend portfolio project using modern backend engineering practices, suitable for junior / intermediate Java backend developer roles.
