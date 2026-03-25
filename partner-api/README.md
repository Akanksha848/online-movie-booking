# Partner API

B2B service for theater partner onboarding and management. Runs on port **8081** with context path `/partner-api`.

## Prerequisites

- Java 17+ (Maven uses the JDK on your `PATH`)
- Maven 3.6+
- Oracle DB (production) **or** nothing extra (dev profile uses H2)

## Running locally

```bash
# With H2 in-memory database (no Oracle required)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# With Oracle (update credentials in application.yml first)
mvn spring-boot:run
```

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/partner-api/api/partners` | Register a new partner |
| GET | `/partner-api/api/partners` | List all partners |
| GET | `/partner-api/api/partners/{id}` | Get partner by ID |
| PATCH | `/partner-api/api/partners/{id}/activate` | Activate a partner |
| POST | `/partner-api/api/partners/{id}/theaters` | Create a theater |
| GET | `/partner-api/api/partners/{id}/theaters` | List partner's theaters |
| GET | `/partner-api/api/partners/{id}/theaters/{theaterId}` | Get theater by ID |

## Docs & Tools (dev profile)

| URL | Description |
|-----|-------------|
| `http://localhost:8081/partner-api/swagger-ui.html` | Swagger UI |
| `http://localhost:8081/partner-api/api-docs` | OpenAPI JSON |
| `http://localhost:8081/partner-api/h2-console` | H2 console (dev only) |

## Configuration

| Profile | Database | `application.yml` |
|---------|----------|-------------------|
| default | Oracle at `localhost:1521` | `application.yml` |
| dev | H2 in-memory | `application-dev.yml` |

## Build

```bash
mvn compile       # compile only
mvn package       # build jar → target/partner-api-1.0.0.jar
java -jar target/partner-api-1.0.0.jar --spring.profiles.active=dev
```
