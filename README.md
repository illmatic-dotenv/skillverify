# SkillVerify — Freelancer Skill Verification Platform

A full-stack microservices platform where companies post skill tests and freelancers take them to earn verified digital badges.

---

## Tech Stack

- **Backend:** Java 21, Spring Boot 3, Spring Security 6, JDBC (no ORM), JWT  
- **Architecture:** Microservices (Auth Service + Exam Service), Spring Cloud Gateway  
- **Database:** PostgreSQL (separate DB per service)  
- **Build:** Maven multi-module  
- **Frontend:** React, Axios, React Router  

---

## Architecture

```
React (port 3000)
   │
   ▼
Auth Service (port 8081)  ←→  authdb (PostgreSQL)
Exam Service (port 8082)  ←→  examdb (PostgreSQL)
```

---

## How It Works

1. **Companies** register, login, and post skill exams with multiple choice questions  
2. **Freelancers** register, login, browse exams, and submit answers  
3. If a freelancer passes, a **verified badge** is issued and stored  
4. JWT tokens are issued by Auth Service and validated independently by Exam Service — fully stateless  

---

## How to Run

### Prerequisites

- Java 21  
- Maven  
- Node.js  
- PostgreSQL  

---

### 1. Create Databases

```sql
CREATE DATABASE authdb;
CREATE DATABASE examdb;
```

---

### 2. Create Tables

Run the SQL in `auth-service` and `exam-service` README sections.

---

### 3. Start Auth Service

```bash
cd auth-service
mvn spring-boot:run
```

---

### 4. Start Exam Service

```bash
cd exam-service
mvn spring-boot:run
```

---

### 5. Start React Frontend

```bash
cd frontend
npm install
npm start
```

---

### 6. Open the App

Go to: http://localhost:3000

---

## API Endpoints

### Auth Service (port 8081)

| Method | Endpoint         | Description              |
|--------|------------------|--------------------------|
| POST   | /auth/register   | Register a new user      |
| POST   | /auth/login      | Login and get JWT token  |

---

### Exam Service (port 8082)

| Method | Endpoint                  | Description                          |
|--------|---------------------------|--------------------------------------|
| GET    | /exams                    | Get all exams                        |
| POST   | /exams/create             | Create a new exam (Company only)     |
| GET    | /exams/{id}/questions     | Get exam questions                   |
| POST   | /exams/{id}/submit        | Submit answers                       |
| GET    | /exams/badges             | Get my badges                        |
| GET    | /exams/attempts           | Get my attempts                      |

---

## Features

- Role-based access control (ROLE_COMPANY / ROLE_FREELANCER)  
- Stateless JWT authentication across independent services  
- Raw JDBC with hand-crafted SQL (no JPA/Hibernate)  
- Separate PostgreSQL database per microservice  
- React frontend with role-aware dashboard  

---