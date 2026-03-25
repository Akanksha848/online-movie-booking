# Cinebook API

B2C REST API for end customers to browse and book movie tickets.

## Tech Stack

- Java 11
- Spring Boot 2.7.18
- Spring Data JPA
- Oracle Database
- Lombok
- Springdoc OpenAPI (Swagger UI)

## Prerequisites

- JDK 11
- Maven 3.6+
- Oracle Database (running on `localhost:1521`)

## Configuration

Database and server settings are in `src/main/resources/application.yml`.

| Property | Default |
|---|---|
| Server port | `8082` |
| Context path | `/cinebook-api` |
| DB URL | `jdbc:oracle:thin:@localhost:1521:ORCL` |
| DB username | `cinebook_user` |
| DB password | `cinebook_password` |

## Running the Application

```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8082/cinebook-api`

## API Documentation

Swagger UI: `http://localhost:8082/cinebook-api/swagger-ui.html`

OpenAPI spec: `http://localhost:8082/cinebook-api/api-docs`

## Endpoints

### Browse

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/browse/movies` | Search movies by city, language, genre |
| GET | `/api/browse/shows` | Find theaters showing a movie on a date |
| GET | `/api/browse/theaters` | Get theaters in a city |

### Bookings

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/bookings` | Create a new booking |
| GET | `/api/bookings/{bookingReference}` | Get booking by reference |
| DELETE | `/api/bookings/{bookingReference}` | Cancel a booking |

### Sample Booking Request

```json
POST /api/bookings
{
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "customerPhone": "9876543210",
  "showId": 101,
  "seatIds": [1, 2, 3],
  "paymentMethod": "CREDIT_CARD"
}
```

### Sample Booking Response

```json
{
  "bookingId": 1,
  "bookingReference": "BKG-A1B2C3D4",
  "customerName": "John Doe",
  "showId": 101,
  "seatIdentifiers": ["SEAT-1", "SEAT-2", "SEAT-3"],
  "totalAmount": 600.00,
  "status": "CONFIRMED",
  "paymentStatus": "COMPLETED",
  "bookedAt": "2026-03-25T10:00:00"
}
```

## Health Check

```
GET http://localhost:8082/cinebook-api/actuator/health
```
