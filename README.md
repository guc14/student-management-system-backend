# 🎓 Student Management System (Spring Boot + MySQL + Swagger)

A clean backend system built with **Java 17**, **Spring Boot 3**, **Spring Data JPA**, and **MySQL**.

The project demonstrates real-world backend development skills: RESTful APIs, DTO pattern, one-to-one & many-to-many relationships, pagination, search filters, validation, and fully interactive **Swagger / OpenAPI** documentation.

---

## 🧰 Tech Stack

- Java 17  
- Spring Boot 3  
- Spring MVC  
- Spring Data JPA (Hibernate)  
- MySQL 8  
- Swagger / OpenAPI  
- Maven  

---

## 🏗 System Overview

This application includes:

- Students  
- Student Profile (1:1)  
- Courses  
- Enrollment (Many-to-Many)  
- Search + filtering + pagination  

---

## 🔶 Features

### Student Management
- CRUD  
- Pagination  
- Keyword + age filtering  

### Student Profile
- Create / Update / Delete  
- GET `/students/{id}/profile`  

### Course Management
- CRUD  
- Students in a course  
- Courses taken by a student  

### Enrollment System
- Student enrolls in course  
- Enrollment details (DTO)  

---

## 🗂 Project Structure

src/main/java/com/guc/studentmanagement
├── controller
├── dto
├── model
├── repository
├── service
└── exception

---

## 🧩 Architecture Diagram

Controller → Service → Repository → MySQL

---

## 🗄 ER Diagram

Student (1) ─── (1) StudentProfile

Student (M) ─── (M) Course
via Enrollment (middle table)

---

## 📚 API Documentation (Swagger UI)

Swagger URL:  
http://localhost:8080/swagger-ui/index.html

---

## 🚀 How to Run

### 1. Clone

```bash
git clone https://github.com/guc14/student-management-system.git
cd student-management-system
```
### 2. Create MySQL Database
```sql
CREATE DATABASE student_db;
```
### 3. Configure application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
### 4. Run the project

In IntelliJ IDEA:

- Open `DemoApplication`
- Click **Run ▶️**

Or run from command line:

```bash
mvn spring-boot:run
```

## 🔍 Technical Highlights

- DTO pattern  
- One-to-One Relation (Student ↔ StudentProfile)  
- Many-to-Many with custom Enrollment table  
- Global exception handler  
- Pagination with Page + Pageable  
- Search filters  
- Swagger documentation using @Tag / @Operation / @Parameter  

---

## 🔮 Future Enhancements

- JWT authentication  
- Docker support  
- Deployment (Render / Railway / AWS)  
- Additional modules (attendance, scheduling)  

---

A complete backend portfolio project suitable for junior Java backend roles.
