FROM openjdk:8-jdk-alpine as build
WORKDIR /app

EXPOSE 8888

COPY target/geolocation-service-1.0.0-RELEASE.jar geolocation-service-1.0.0-RELEASE.jar

ENTRYPOINT ["java", "-jar", "geolocation-service-1.0.0-RELEASE.jar"]