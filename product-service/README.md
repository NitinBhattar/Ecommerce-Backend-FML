# 📦 Product Service

Manages product and category operations for the E-Commerce Backend, including product search, advance filtering, and image uploads.

### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/categories/create` | Create a category |
| `PUT` | `/api/categories/{id}` | Update a category |
| `DELETE` | `/api/categories/{id}` | Delete a category |
| `GET` | `/api/categories/{id}` | Get category by ID |
| `GET` | `/api/categories` | Get all categories |
| `POST` | `/api/products/create` | Create a product |
| `PUT` | `/api/products/image-upload/{id}` | Upload product image |
| `POST` | `/api/products/{id}` | Update a product |
| `DELETE` | `/api/products/{id}` | Delete a product |
| `GET` | `/api/products/{id}` | Get product by ID |
| `GET` | `/api/products` | Get all products with pagination and sorting |
| `GET` | `/api/products/search` | Search products by keyword |
| `GET` | `/api/products/filter/category` | Filter products by category |
| `GET` | `/api/products/filter/price` | Filter products by price range |
| `GET` | `/api/products/filter/advance` | Filter products by keyword, category, price, and brand |
| `GET` | `/api/products/images/{fileName}` | Retrieve a product image |


### Tech Stack
Java · Spring Boot · Spring Data JPA · JPQL · MySQL

### Run Locally
```bash
./mvnw spring-boot:run
```

**Port:** `8083`

📄 Licensed under the [MIT License](../LICENSE).
