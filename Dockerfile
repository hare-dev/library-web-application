# First stage: build the application
FROM maven:3.8.3-openjdk-17 AS build
COPY . /application
WORKDIR /application
RUN --mount=type=cache,target=/root/.m2 mvn -f /application/pom.xml clean package

# Second stage: create a slim image
FROM openjdk:17
COPY --from=build /application/target/library-0.0.1-SNAPSHOT.jar /application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]