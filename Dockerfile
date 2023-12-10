FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/springboot-example.jar springboot-example.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","springboot-example.jar"]