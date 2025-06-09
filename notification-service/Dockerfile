# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/notification-service-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Allow overriding properties via environment variables
ENV SPRING_MAIL_HOST=sandbox.smtp.mailtrap.io
ENV SPRING_MAIL_PORT=587
ENV SPRING_MAIL_USERNAME=1434fca4fc60ac
ENV SPRING_MAIL_PASSWORD=706f55a2b63ef0

# 🔁 CHANGED THIS LINE
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092

ENV SPRING_KAFKA_CONSUMER_GROUP_ID=notification-service-group
ENV SPRING_KAFKA_CONSUMER_KEY_DESERIALIZER=org.apache.kafka.common.serialization.StringDeserializer
ENV SPRING_KAFKA_CONSUMER_VALUE_DESERIALIZER=io.confluent.kafka.serializers.KafkaAvroDeserializer
ENV SPRING_KAFKA_CONSUMER_PROPERTIES_SCHEMA_REGISTRY_URL=http://schema-registry:8081
ENV SPRING_KAFKA_CONSUMER_PROPERTIES_SPECIFIC_AVRO_READER=true

ENTRYPOINT ["java", "-jar", "app.jar"]
