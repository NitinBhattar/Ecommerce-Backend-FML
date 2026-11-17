# 🧭 Service Discovery Server

Provides centralized service registration and discovery for the E-Commerce Backend microservices using Netflix Eureka.

### Features

- **Service Registration:** Allows microservices to register themselves with the Eureka Server.
- **Service Discovery:** Enables services to discover and communicate with other registered services.
- **Health Monitoring:** Tracks service availability through periodic heartbeats.

### Tech Stack

Java · Spring Boot · Spring Cloud Netflix Eureka

### Run Locally

```bash
./mvnw spring-boot:run
```

**Port:** `8761`

### Dashboard

http://localhost:8761

📄 Licensed under the [MIT License](../LICENSE).