#--For build stage--
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#--For runtime stage
From eclipse-temurin-17
WORKDIR /app
COPY --from=build /app/target/userservice.jar userservice.jar
