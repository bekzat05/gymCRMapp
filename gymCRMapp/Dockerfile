FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/gym-0.0.1-SNAPSHOT.jar /app/gym-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gym-app.jar"]