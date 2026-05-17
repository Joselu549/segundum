FROM maven:3.9-eclipse-temurin-11-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B -q
COPY src ./src
RUN mvn package -DskipTests -B -q

FROM eclipse-temurin:11-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/productos \
    SPRING_DATASOURCE_USERNAME=root \
    SPRING_DATASOURCE_PASSWORD=root \
    SPRING_RABBITMQ_ADDRESSES=amqp://user:password@rabbitmq:5672

EXPOSE 61072
CMD ["java", "-jar", "app.jar"]
