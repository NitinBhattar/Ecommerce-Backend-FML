# 📦 Inventory Service

Manages product inventory, stock availability, and inventory operations for the E-Commerce Backend.

### APIs
### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/inventory/add` | Add inventory for a SKU |
| `GET` | `/api/inventory/{skuCode}` | Check stock availability and quantity by SKU |


### Tech Stack

Java · Spring Boot · Spring Data JPA · MySQL · Apache Kafka · OpenFeign

### Run Locally

```bash
./mvnw spring-boot:run
```

**Port:** `8085`

📄 Licensed under the [MIT License](../LICENSE).
