FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN ./mvnw dependency:resolve

COPY src src

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/api-rest-sb3-0.0.1-SNAPSHOT.jar"]