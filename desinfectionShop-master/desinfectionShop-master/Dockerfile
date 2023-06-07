#
# Build stage
#
FROM maven:3.8.1-openjdk-17-slim AS build
LABEL stage=builder
COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install -DskipTests


#
# Package stage
#
FROM openjdk:17-jdk-slim
ENV TZ=Europe/Moscow
COPY --from=build /app/target/app-0.0.1-SNAPSHOT.jar /app/app.jar
COPY images /app/images
WORKDIR /app
ENTRYPOINT ["java","-jar","app.jar"]
