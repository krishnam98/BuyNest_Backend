# 🔧 BuyNest Backend

This is the backend of the **BuyNest** e-commerce application, developed using **Spring Boot**, **Spring Security**, **Spring Data JPA**, **MySQL**, and **JWT**. It handles REST APIs for user authentication, product management, cart, orders, and seller analytics.

---

## ⚙️ Tech Stack

- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Tokens)

---

## 🚀 Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/buynest-backend.git
cd buynest-backend
```

2️⃣ 📦 Configure MySQL Database
Make sure MySQL is installed and running.

Create a database named amazon:

CREATE DATABASE amazon;

3️⃣ ⚙️ Configure Application Properties
Check the file:
src/main/resources/application.properties

spring.application.name=backend

spring.datasource.url=jdbc:mysql://localhost:3306/amazon
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver


spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
.

4️⃣ 🏃 Run the Application
Option 1: Using Maven Wrapper


./mvnw spring-boot:run

Option 2: From IDE

Run BuyNestApplication.java as a Spring Boot app.