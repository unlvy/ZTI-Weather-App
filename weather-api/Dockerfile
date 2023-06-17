FROM maven:3.9.2-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/weather-api-1.0.jar weather-api-1.0.jar
EXPOSE 443
ENTRYPOINT ["java", "-jar", "weather-api-1.0.jar"]