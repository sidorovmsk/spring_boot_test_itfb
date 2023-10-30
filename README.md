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

GET /api/users <br>
GET /user_json/{id} <br>
DELETE /user_delete/{id} <br>
POST /user_edit/{id} <br>
POST /registration <br>
GET /api/about <br>
GET /api/authors <br>
GET /api/author/{id} <br>
PUT /author_edit/{id} <br>
POST /create/author <br>
DELETE /author_delete/{id} <br>
GET /api/books <br>
GET /api/book/{id} <br>
PUT /book_edit/{id} <br>
POST /create/book <br>
GET /findbooks?{sometext} <br>
DELETE /book_delete/{id} <br>
GET /metrics

Web Interface Methods.
The web interface methods include:

GET /users <br>
GET /user/{id} <br>
GET /user_edit/{id} <br>
GET /login <br>
GET /registration <br>
GET /about <br>
GET /logout <br>
GET /authors <br>
GET /author/{id} <br>
GET /author_edit/{id} <br>
GET /create/author <br>
GET /books <br>
GET /book/{id} <br>
GET /book_edit/{id} <br>
GET /create/book <br>
GET /findbooks
