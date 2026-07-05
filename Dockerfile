FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src src
RUN mvn -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

ENV SERVER_PORT=8081
ENV MEDIA_LOCATION=uploads

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]