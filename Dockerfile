FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app
RUN apt-get update && apt-get install -y maven
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=builder /app/target/Graduation-Project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]