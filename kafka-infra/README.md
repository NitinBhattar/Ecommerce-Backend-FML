# 📨 Kafka Configuration

Docker Compose configuration for Apache Kafka in KRaft mode for the E-Commerce Backend microservices.

### Configuration

| Property | Value |
|---|---|
| Image | `apache/kafka:4.4.0` |
| Mode | KRaft (no ZooKeeper) |
| Broker Port | `9092` |
| Internal Listener | `29092` |
| Controller Port | `9093` |
| Storage | Docker named volume |

### Run Locally

Start Kafka:

```bash
docker compose up -d
```

View logs:

```bash
docker compose logs -f kafka
```

Stop Kafka:

```bash
docker compose down
```

### Connection

- **Local applications:** `localhost:9092`
- **Dockerized services:** `kafka:29092`

### Tech Stack

Apache Kafka · Docker · Docker Compose

📄 Licensed under the [MIT License](../LICENSE).