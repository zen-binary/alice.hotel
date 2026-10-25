# alice.hotel.backend

## Requirements

For building and running the application you need:

- [JDK 17](https://adoptium.net/)
- [Gradle](https://gradle.org/) (or the included Gradle Wrapper)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.alice.hotel.api.Application` class from your IDE.

API base path: `http://localhost:8080/api/v1`

## Build WAR

```bash
./gradlew bootWar
```

Output: `ALICE_HOTEL_ROOT.war`

## Standards

Project-wide conventions: see [STANDARDS.md](../STANDARDS.md) at the monorepo root.
