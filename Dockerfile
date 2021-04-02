# FROM openjdk:15-jdk-alpine
# WORKDIR /home
# EXPOSE 8080
# ARG JAR_FILE
# ADD ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3.6.3-openjdk-15-slim AS MAVEN_BUILD

WORKDIR /build
COPY pom.xml .
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

# package our application code
COPY src/ /build/src/
RUN mvn clean package

# the second stage of our build will use open jdk 8 on alpine 3.9
FROM openjdk:15-jdk-alpine
EXPOSE 8080
# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /build/target/wallet-0.0.1-SNAPSHOT.jar /app.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/app.jar"]