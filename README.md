# 🎓 Student Management System (Spring Boot + MySQL + Swagger)

A clean, modular backend system built with **Java 17**, **Spring Boot 3**, **Spring Data JPA**, and **MySQL**.

This project demonstrates real-world backend development skills: RESTful APIs, DTO pattern, one-to-one & many-to-many relationships, pagination, search filters, validation, and fully interactive **Swagger / OpenAPI** documentation.

> Designed as a portfolio project for **Java backend developer** roles.

---

## 🧰 Tech Stack

- **Language:** Java 17  
- **Framework:** Spring Boot 3, Spring MVC  
- **Persistence:** Spring Data JPA (Hibernate), ORM  
- **Database:** MySQL 8  
- **API Docs:** Swagger / OpenAPI (springdoc-openapi)  
- **Tools:** Maven, Lombok, Postman / Swagger UI  
- **Architecture:** Controller → Service → Repository → Entity

---

## 🏗 System Overview

This system manages:

- **Students**
- **Courses**
- **Student Profile** (One-to-One)
- **Enrollments** (Many-to-Many)

---

# 🔶 Features

### ✔ Student Management
- CRUD  
- Pagination + sorting  
- Keyword + age range search  

### ✔ Student Profile (1:1)
- Create / Update / Delete  
- GET `/students/{id}/profile`

### ✔ Course Management
- CRUD  
- Students in a course  
- Courses taken by a student

### ✔ Enrollment System (M:N)
- Enroll student into course  
- Query by student  
- Query by course  
- EnrollmentInfo combined DTO

### ✔ Swagger Documentation
- Fully documented  
- Module grouping  
- Summary + description  
- Try It Out support

---

# 🗂 Project Structure

src/main/java/com/example/demo
│
├── controller
├── dto
├── model
├── repository
├── service
└── exception

yaml
Copy code

---

# 🧩 Architecture Diagram

Controller → Service → Repository → MySQL

yaml
Copy code

---

# 🗄 ER Diagram

Student (1) ─── (1) StudentProfile

Student (M) ─── (M) Course
↳ Enrollment (middle table)

yaml
Copy code

---

# 📚 API Documentation (Swagger UI)

Swagger URL:  
**http://localhost:8080/swagger-ui/index.html**

---

🚀 How to Run
1) Clone
git clone https://github.com/your-username/student-management-system.git
cd student-management-system

2) MySQL Database
CREATE DATABASE student_db;

3) application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

4) Run

在 IntelliJ IDEA 中：

找到 DemoApplication

点击 Run ▶️

或者命令行运行：

mvn spring-boot:run
