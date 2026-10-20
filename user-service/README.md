# 👤 User Service

Manages user registration, authentication, and access token refreshing for the E-Commerce Backend.

### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users/register` | Register a user |
| `POST` | `/api/users/login` | Authenticate a user |
| `GET` | `/api/users/{id}` | Get user by ID |
| `POST` | `/api/auth/refreshAccessToken` | Refresh access token |

### Tech Stack
Java · Spring Boot · Spring Security · JWT · MySQL

### Run Locally
```bash
./mvnw spring-boot:run
```

**Port:** `8082`

📄 Licensed under the [MIT License](../LICENSE).