FROM maven:3.9.6-eclipse-temurin-21 AS build-stage

WORKDIR /app

COPY pom.xml .
COPY src ./src

#RUN mvn dependency:go-ffline -B

RUN mvn clean install -DskipTests

FROM openjdk:21

WORKDIR /app

COPY --from=build-stage /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
