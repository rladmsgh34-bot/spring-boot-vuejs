# Multi-stage build for Spring Boot + Vue.js application

# Stage 1: Build frontend
FROM node:16-alpine AS frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Stage 2: Build backend
FROM maven:3.8.6-openjdk-17-slim AS backend-build
WORKDIR /app
COPY pom.xml ./
COPY backend/pom.xml backend/
COPY frontend/pom.xml frontend/
RUN mvn dependency:go-offline -B

COPY backend/src backend/src
COPY --from=frontend-build /app/frontend/target/dist backend/src/main/resources/public
RUN mvn clean package -DskipTests -pl backend -am

# Stage 3: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Create non-root user
RUN useradd -m -u 1001 appuser && \
    chown -R appuser:appuser /app

COPY --from=backend-build --chown=appuser:appuser /app/backend/target/*.jar app.jar

USER appuser

EXPOSE 8088

ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=production

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8088/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

