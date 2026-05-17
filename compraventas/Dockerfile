FROM maven:3.9-eclipse-temurin-11-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B -q
COPY src ./src
RUN mvn package -DskipTests -B -q

FROM eclipse-temurin:11-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_DATA_MONGODB_URI=mongodb://root:example@mongo:27017/compraventas?authSource=admin \
    SPRING_RABBITMQ_ADDRESSES=amqp://user:password@rabbitmq:5672 \
    MICROSERVICIOS_USUARIOS_URL=http://usuarios:8080/ \
    MICROSERVICIOS_PRODUCTOS_URL=http://productos:61072/

EXPOSE 61070
CMD ["java", "-jar", "app.jar"]
