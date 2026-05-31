# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy project structure
COPY pom.xml ./
COPY Seguridad/Autorization-server/pom.xml ./Seguridad/Autorization-server/
COPY commons/ ./commons/
COPY DominioNegocio/ ./DominioNegocio/
COPY Infraestructura/ ./Infraestructura/
COPY Seguridad/ ./Seguridad/

# Build the project
RUN mvn -B clean package -DskipTests -pl Seguridad/Autorization-server -am

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S paymentchain && adduser -S admin -G paymentchain

WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/Seguridad/Autorization-server/target/Seguridad-0.0.1-SNAPSHOT.jar app.jar

USER admin:paymentchain

ENV JAVA_OPTS="-Xms256M -Xmx256M"

EXPOSE 9000

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]