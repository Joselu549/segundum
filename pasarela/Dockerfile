FROM maven:3.9-eclipse-temurin-11-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B -q
COPY src ./src
RUN mvn package -DskipTests -B -q

FROM eclipse-temurin:11-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENV ZUUL_ROUTES_USUARIOS_URL=http://usuarios:8080/users \
    ZUUL_ROUTES_PRODUCTOS_URL=http://productos:61072/products \
    ZUUL_ROUTES_COMPRAVENTAS_URL=http://compraventas:61070/compraventas \
    USUARIOS_URL=http://usuarios:8080

EXPOSE 8090
CMD ["java", "-jar", "app.jar"]
