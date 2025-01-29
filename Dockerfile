FROM openjdk:8-jdk-alpine as build

EXPOSE 8888

ADD . /src/main/resources/static

ADD target/geolocation-service-1.0.0-RELEASE.jar .

ENTRYPOINT ["java", "-jar", "geolocation-service-1.0.0-RELEASE.jar"]