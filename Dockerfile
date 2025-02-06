# Stage 1: Build the application using Gradle
FROM gradle:8.0.2-jdk17 AS builder
WORKDIR /app

# Copy Gradle wrapper and configuration files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .

# Download project dependencies
RUN ./gradlew dependencies --no-daemon

# Copy the source code and build the application
COPY src src
RUN ./gradlew bootJar --no-daemon

# Stage 2: Create the final image for running the application
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port 8080 for the Spring Boot application
EXPOSE 8080

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

# Instructions to build and run the Docker container
# 1. Build the Docker image:
#    docker build -t xlsx-max-number-service .
# 2. Run the container:
#    docker run -p 8080:8080 xlsx-max-number-service
