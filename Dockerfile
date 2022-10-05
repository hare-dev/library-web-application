# Maven Build

FROM maven:3.8.1-openjdk-17 AS builder
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

# Run
FROM openjdk:17
COPY --from=builder /app/target/library-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]