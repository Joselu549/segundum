FROM maven:3.9-eclipse-temurin-11-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B -q
COPY src ./src
RUN sed -i 's|localhost:3306|mysql:3306|g' src/main/resources/META-INF/persistence.xml
RUN mvn package -DskipTests -B -q

FROM tomcat:9.0-jdk11-temurin-jammy
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
# COPY entrypoint.sh /entrypoint.sh
# RUN chmod +x /entrypoint.sh

ENV RABBITMQ_URI=amqp://user:password@rabbitmq:5672

# EXPOSE 8080
# ENTRYPOINT ["/entrypoint.sh"]
# CMD ["catalina.sh", "run"]
