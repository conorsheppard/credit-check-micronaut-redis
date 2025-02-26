FROM amazoncorretto:21-alpine-jdk
ARG JAR_FILE=target/credit-check-micronaut-0.1.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application-compose.yml application-compose.yml
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080/tcp
