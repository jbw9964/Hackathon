FROM eclipse-temurin:21-jre
WORKDIR /hackathon

ARG JAR_FILE=build/libs/Hackathon-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "/hackathon/application.jar"]
