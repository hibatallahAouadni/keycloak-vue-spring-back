FROM openjdk:11.0.4-jdk-slim

COPY /build/libs/spring-keycloak-back-1.0.0.jar /app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]