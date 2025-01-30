# Spring Boot geolocation-service 
### Description
REST API that provides auto-complete suggestions for large cities in USA and Canada

### Setup Project
To run this repo, please follow these command:
1. Install java 
2. Run project by paste this command to your terminal
   `mvn clean spring-boot:run`
3. For hit endpoint you can use [swagger-ui](http://localhost:8888/geolocation/swagger-ui/index.html)

Or you can run this project on docker container with these command:
1. Build docker image `docker build -t geolocation-service .`
2. Run Docker container `docker run -p 8888:8888 geolocation-service`
3. For hit endpoint you can use [swagger-ui](http://localhost:8888/geolocation/swagger-ui/index.html)