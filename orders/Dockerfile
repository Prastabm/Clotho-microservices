# syntax=docker/dockerfile:1
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copy the built jar from the target directory
COPY target/orders-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]