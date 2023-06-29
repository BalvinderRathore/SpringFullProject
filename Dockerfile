FROM openjdk:11-ea-9-jdk-slim
COPY target/SpringFullProject-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]