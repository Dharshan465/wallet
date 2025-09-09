# Product Management API (Layered Architecture with Spring Boot and In-Memory Map)


---

This project implements a Product Management API using a layered architecture with Spring Boot and an in-memory Map for data storage. The application supports standard CRUD (Create, Read, Update, Delete) operations, along with a search functionality, and features centralized exception handling to return clean JSON error messages.

---

### Project Structure:
```
assignment14.ProductManagementAPI/
        ├── ProductManagementAPI.java (Main Spring Boot Application)
        ├── Controller/
        │   └── ProductController.java
        ├── Model/
        │   └── Product.java
        ├── ProductException/ (Package for exceptions and error handling components)
        │   ├── ErrorResponse.java
        │   ├── GlobalExceptionHandler.java
        │   ├── InvalidProductDataException.java
        │   ├── ProductException.java 
        │   └── ProductNotFoundException.java
        └── Service/
            ├── ProductService.java(Interface)
            └── ProductServiceImplementation.java

```

---

### Key Components:

#### ProductController.java
- Handles HTTP requests and responses for product-related operations.
- Uses `@RestController` and `@RequestMapping("/products")` annotations.
- Injects `ProductService` using `@Autowired`.
- Endpoints:
    - `POST /products`: Adds a new product.
    - `GET /products/{id}`: Retrieves a product by its ID.
    - `GET /products`: Retrieves all products.
    - `PUT /products/{id}`: Updates an existing product's details.
    - `PATCH /products/{id}/stock`: Updates only the stock quantity of a product.
    - `DELETE /products/{id}`: Deletes a product by its ID.
    - `GET /products/search?name=keyword`: Searches for products by a keyword in their name.


#### GlobalExceptionHandler.java
- Provides centralized exception handling for the API.
- Uses `@RestControllerAdvice` and `@ExceptionHandler` annotations.
- Custom exceptions handled:
    - `ProductNotFoundException`: Returns HTTP 404 Not Found for non-existent products.
    - `InvalidProductDataException`: Returns HTTP 400 Bad Request for invalid product data (e.g., blank name, non-positive price, negative stock).
    - `Exception / ProductException`: Catches any other unhandled exceptions, returning HTTP 500 Internal Server Error.
- Returns `ResponseEntity<ErrorResponse>` with a consistent JSON error format.


#### Service Methods:
- addProduct
- getProductById
- getAllProducts
- updateProduct
- updateStock
- deleteProduct
- searchProductsByName


#### Model
- Product.java: A simple POJO (Plain Old Java Object) representing a product.
- Fields: id (Integer), name (String), price (Double), stock (Integer).

#### Custom ErrorResponse DTO (ErrorResponse.java)
- A POJO designed to structure the JSON error responses.
- Fields: status (int), error (String), message (String), path (String), timestamp (LocalDateTime).


#### In-memory Map:
- Simulates a database using Map<Integer, Product> (productRepo) for data storage.


## GitHub Repository: https://github.com/Dharshan465/wallet/tree/main/src/main/java/ford/assignment14

---
