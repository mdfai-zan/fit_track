# Fit Tracker
> FitTrack is a fitness management application designed to help users monitor and improve their workout session.

---

## 📖 About

This is a Fitness Tracking REST API built with Spring Boot. It allows users to register, log workouts, receive activity recommendations, and securely authenticate using JWT.

---

## ✨ Features

- User Authentication (JWT)
- User Registration & Login
- Activity Management
- Recommendation System
- Role-based Authorization
- RESTful APIs
- MySQL Database

---

## 🛠 Tech Stack

Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

Database
- MySQL

Tools
- Maven
- Git
- Postman

---

## 📂 Project Structure

src
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── security
 ├── dto
 └── config


---

## 🔐 API Authentication

Use JWT Token after login.

Authorization: Bearer <your_token>

---

## 📮 API Endpoints

| Method | Endpoint | Description |
|---------|-----------|------------|
| POST | /api/user/register | Register User |
| POST | /api/user/login| Login |
| GET | /api/activity | Get Activities |
| POST | /api/activity | Add Activity |
| POST | /api/recommendation/generate | Generate recommendation |
| POST | /api/recommendation/user/{id} | Get user recommendation |
| POST | /api/recommendation/activity/{id} | Get activity recommendation |

---
