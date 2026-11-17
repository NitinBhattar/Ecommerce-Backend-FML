# 💳 Payment Service

Manages payment creation, payment status, and payment gateway integration for the E-Commerce Backend.

### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/payment` | Create a payment |
| `GET` | `/api/payment/order/{orderId}` | Get payment by order ID |
| `PUT` | `/api/payment/{paymentId}/status` | Update payment status |
| `POST` | `/api/payment/webhook/razorpay` | Handle Razorpay webhook events |

### Tech Stack

Java · Spring Boot · Spring Data JPA · MySQL · Razorpay · OpenFeign · Micrometer Tracing · Zipkin

### Run Locally

```bash
./mvnw spring-boot:run
```

**Port:** `8086`

📄 Licensed under the [MIT License](../LICENSE).