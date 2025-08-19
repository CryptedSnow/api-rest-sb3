# Use JDK 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and build files first (for caching)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy the source code
COPY src src

# Build the app using Maven wrapper
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot jar (adjust jar name if different)
CMD ["java", "-jar", "target/api-rest-sb3-0.0.1-SNAPSHOT.jar"]