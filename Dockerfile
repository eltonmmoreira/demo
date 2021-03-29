FROM openjdk:15-jdk-alpine
WORKDIR /home
EXPOSE 8080
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]