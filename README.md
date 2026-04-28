# 🎓 Student Management System (JDBC + MySQL)

## 📌 Project Overview
This is a console-based Student Management System built using Java and JDBC.  
It allows users to perform CRUD operations on student records stored in a MySQL database.

---

## 🚀 Features
- Add new student
- View all students
- Update student marks
- Delete student record
- Search student by ID
- Show topper(s) using SQL MAX()
- Count total number of students
- Input validation and duplicate ID handling

---

## 🛠️ Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- SQL

---

## 🗄️ Database Schema

```sql
CREATE DATABASE studentdb;

USE studentdb;

CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    marks DOUBLE
);
