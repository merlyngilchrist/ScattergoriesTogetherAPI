FROM openjdk:23-jdk-slim

WORKDIR /ScattergoriesTogetherAPI

COPY target/ScattergoriesTogetherAPI-0.0.1-SNAPSHOT.jar ScattergoriesTogetherAPI-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ScattergoriesTogetherAPI-0.0.1-SNAPSHOT.jar"]