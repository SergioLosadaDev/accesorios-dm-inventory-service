<<<<<<< HEAD
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Instalar wget para healthcheck
RUN apk add --no-cache wget

# Crear usuario no root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copiar el JAR
COPY target/inventory-service-0.0.1-SNAPSHOT.jar app.jar

# Cambiar propietario
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=5 \
  CMD wget -qO- http://localhost:8082/api/v1/health || exit 1

ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", "app.jar"]
=======
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -q
COPY src ./src
RUN ./mvnw clean package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
>>>>>>> b4883ed0b5faca54e1ebb9465472557e808ca36f
