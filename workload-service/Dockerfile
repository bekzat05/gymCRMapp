FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/workload-service-0.0.1-SNAPSHOT.jar /app/workload-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "workload-service.jar"]