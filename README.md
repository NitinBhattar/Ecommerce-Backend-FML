# 🛒 E-Commerce Backend

A **microservices-based e-commerce backend** built with Spring Boot, designed for modularity, service discovery, distributed tracing, event-driven communication, and payment integration.

## 🚀 Tech Stack

- **Java & Spring Boot** — Backend services
- **Spring Cloud Gateway** — API gateway
- **Netflix Eureka** — Service discovery
- **Spring Data JPA & JPQL** — Database access and query optimization
- **MySQL** — Relational database
- **Apache Kafka** — Event-driven communication
- **Docker** — Containerization
- **Micrometer Tracing & Zipkin** — Distributed tracing and request monitoring
- **Twilio** — SMS notifications
- **Razorpay** — Payment gateway

## 🏗️ Services & Ports

| Service | Port |
|---|---:|
| Main Application | `8080` |
| API Gateway | `8081` |
| User Service | `8082` |
| Product Service | `8083` |
| Order Service | `8084` |
| Inventory Service | `8085` |
| Payment Service | `8086` |
| Notification Service | `8087` |
| Eureka Server | `8761` |
| MySQL | `3036` |
| Kafka | `9092` |
| Zipkin | `9411` |

## ✨ Highlights

- **Microservices architecture** with independently organized services
- **Service discovery** through Eureka
- **Centralized API routing** through the API Gateway
- **Optimized database queries** using JPQL
- **Event-driven communication** using Kafka
- **Distributed tracing** using Micrometer Tracing and Zipkin
- **Containerized services** using Docker
- **SMS notifications** through Twilio
- **Payment processing** through Razorpay

## ⚙️ Getting Started

### Prerequisites

- Java and Maven
- Docker
- MySQL
- Apache Kafka
- Zipkin
- OpenFeign

### Clone the Repository

```bash
git clone https://github.com/NitinBhattar/Ecommerce-Backend-FML.git
cd Ecommerce-Backend-FML
```

### Run Each Service

Run these commands from the repository root, in separate terminals.

**1. Main Application**

```bash
cd E-Commerce
./mvnw spring-boot:run
```

**2. API Gateway**

```bash
cd api-gateway
./mvnw spring-boot:run
```

**3. Eureka Server**

```bash
cd eureka-server
./mvnw spring-boot:run
```

**4. User Service**

```bash
cd user-service
./mvnw spring-boot:run
```

**5. Product Service**

```bash
cd product-service
./mvnw spring-boot:run
```

**6. Order Service**

```bash
cd order-service
./mvnw spring-boot:run
```

**7. Inventory Service**

```bash
cd inventory-service
./mvnw spring-boot:run
```

**8. Payment Service**

```bash
cd payment-service
./mvnw spring-boot:run
```

**9. Notification Service**

```bash
cd notification-service
./mvnw spring-boot:run
```

> Start MySQL, Kafka, and Zipkin, configure the required environment variables and service settings, and start Eureka before the dependent services. Replace `<main-application-directory>` with the actual directory name if a separate main application exists.

## 📊 Monitoring

- **Eureka Dashboard:** http://localhost:8761
- **Zipkin Dashboard:** http://localhost:9411

## 📚 API Documentation

Each microservice has its own `README.md` containing its endpoints and service-specific details.

## 📄 License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for details.

---

<p>Built with ☕ Java and Spring Boot 🍀</p>