# Flo E-commerce API

A Spring Boot application that provides RESTful API for managing products, categories, and orders in an e-commerce system.

## API Documentation

The API documentation is available via Swagger UI when the application is running:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Specification: http://localhost:8080/v3/api-docs

## Available Endpoints

### Products API

- `POST /api/products` - Create a new product
- `GET /api/products` - Get all products (with optional filtering)
- `GET /api/products/{id}` - Get product by ID
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product

### Categories API

- `POST /api/categories` - Create a new category
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `DELETE /api/categories/{id}` - Delete a category

### Orders API

- `POST /api/orders` - Create a new order
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID

## Running the Application

```bash
./gradlew bootRun
```

The application will start on port 8080, and you can access the API documentation at http://localhost:8080/swagger-ui.html
