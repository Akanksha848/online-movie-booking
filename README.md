# Online Movie Booking System

Complete backend for movie ticketing platform with **Partner API** (B2B theater management) and **Cinebook API** (B2C customer booking). Two independent Spring Boot microservices running on ports **8081** & **8082**.

## Architecture
online-movie-booking/
├── partner-api/          # Port 8081 - Theater partner onboarding 
└── cinebook-api/         # Port 8082 - Customer movie bookings


## Prerequisites
- **Java**: 11+ (Partner API) / 11+ (Cinebook API)
- **Maven**: 3.6+
-  **SpringBoot**: 3.2.0
- **Database**: Oracle (`localhost:1521:ORCL`)


## Build & Deploy
# Build both JARs
mvn clean package -f partner-api/pom.xml
mvn clean package -f cinebook-api/pom.xml

# Run independently
java -jar partner-api/target/*.jar --spring.profiles.active=dev
java -jar cinebook-api/target/*.jar

## Health Checks
	•	Partner:  http://localhost:8081/partner-api/actuator/health 
	•	Cinebook:  http://localhost:8082/cinebook-api/actuator/health 
