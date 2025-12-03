# 🎓 Student Management System (Spring Boot + MySQL)

A clean, modular, production-style Student Management System API built with  
**Java 17, Spring Boot 3, Spring Data JPA, MySQL**,  
and includes **DTO**, **Exception Handling**, **Logging AOP**, and **JPA relationships**.

This project is designed to demonstrate backend development skills suitable for **junior backend developer positions**.

---

## 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Spring Web](https://img.shields.io/badge/Spring_Web-MVC-green)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-ORM-yellow)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Lombok](https://img.shields.io/badge/Lombok-Annotation-green)
![AOP](https://img.shields.io/badge/AOP-Logging-blue)

---

## 📦 Project Structure

```
com.example.demo
├── controller         # REST APIs
├── service            # Business Logic
├── repository         # JPA Repositories
├── model              # Entities (Student, Profile, Course, Enrollment)
├── dto                # Request/Response DTOs
├── mapper             # Convert Entity ↔ DTO
├── exception          # Global handlers & custom exceptions
└── aop                # Logging Aspect
```

---

## ✨ Features

### 🧑‍🎓 Student Management
- Create student  
- Update student  
- Delete student  
- Get student by ID  
- Get all students (DTO formatted)

---

### 📝 Student Profile (1-to-1)
- Create profile for a student  
- Update profile  
- Get profile info  

---

### 📚 Course & Enrollment (Many-to-Many)
- Create course  
- Student enrolls a course  
- Get courses selected by a student  
- Get students enrolled in a course  
- Get a student's enrollment info (with timestamp)

---

## 🔗 API Examples

### ✅ Create Student
```
POST /students
{
  "name": "Anna",
  "age": 20
}
```

### ✅ Get All Students (DTO)
```
GET /students
```

### ✅ Create Profile (1-to-1)
```
POST /profiles/student/1
{
  "email": "anna@test.com",
  "phone": "123456"
}
```

### ✅ Enrollment (Many-to-Many)
```
POST /courses/1/students/2
```

---

## 🏃 Run the Project

### 1️⃣ Configure MySQL
```
CREATE DATABASE student_db;
```

### 2️⃣ Run Spring Boot
```
mvn spring-boot:run
```

---

## 👩 Author

**Chen** — Spring Boot backend developer in training.

If you like this project, please ⭐ star the repo — your support means a lot!

