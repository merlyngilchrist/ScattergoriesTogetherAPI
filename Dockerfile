# Stage 1: Build
FROM openjdk:23-jdk-slim AS builder
WORKDIR /ScattergoriesTogetherAPI
COPY . .
RUN chmod +x mvnw
RUN ./mvnw package

# Stage 2: Run
FROM openjdk:23-jdk-slim 
WORKDIR /ScattergoriesTogetherAPI
COPY --from=builder /ScattergoriesTogetherAPI/target/*.jar ScattergoriesTogetherAPI.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ScattergoriesTogetherAPI.jar"]