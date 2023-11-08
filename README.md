# My Spring Boot Application


This is my Spring Boot application, which is a web application for managing books and authors.

## Requirements

To run this application, you will need:

- Java 11 (or a later version)
- Maven
- PostgreSQL (or another database if you prefer)

## Installation and Configuration

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/my-spring-boot-app.git
   cd my-spring-boot-app

2. Create a database in PostgreSQL and configure the connection parameters in the application.yml file.
3. Start the application: mvn spring-boot:run

Usage

API Endpoints.
The application provides the following API endpoints:

GET /users/list <br>
GET /users/{id} <br>
DELETE /users/{id} <br>
PUT /users/{id} <br>
POST /registration <br>
GET /about <br>
GET /authors <br>
GET /author/{id} <br>
PUT /authors/{id} <br>
POST /authors <br>
DELETE /authors/{id} <br>
GET /books <br>
GET /books/{id} <br>
PUT /books/{id} <br>
POST /books <br>
GET /findbooks?{sometext} <br>
DELETE /books/{id} <br>
GET /metrics

Web Interface Methods.
The web interface methods include:

GET /view/users <br>
GET /view/users/{id} <br>
GET /view/users/edit/{id} <br>
GET /login <br>
GET /registration <br>
GET /view/about <br>
GET /logout <br>
GET /view/authors <br>
GET /view/authors/{id} <br>
GET /view/authors/edit/{id} <br>
GET /view/authors/create <br>
GET /view/books <br>
GET /view/books/{id} <br>
GET /view/books/edit/{id} <br>
GET /view/books/create <br>
GET /findbooks
