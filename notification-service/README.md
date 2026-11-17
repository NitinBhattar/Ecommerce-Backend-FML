# 🔔 Notification Service

Handles asynchronous notifications for the E-Commerce Backend through email and SMS.

### Features

- **Email Notifications:** Sends emails using Java Mail and Gmail SMTP.
- **SMS Notifications:** Sends SMS using Twilio.
- **Event-Driven Processing:** Consumes notification events from Apache Kafka.

### Kafka Configuration

| Property | Value |
|---|---|
| Topic | `notification-event` |
| Consumer Group | `notification-group` |

### Tech Stack

Java · Spring Boot · Spring Mail · Gmail SMTP · Twilio · Apache Kafka · Micrometer Tracing · Zipkin

### Run Locally

```bash
./mvnw spring-boot:run
```

**Port:** `8087`

📄 Licensed under the [MIT License](../LICENSE).