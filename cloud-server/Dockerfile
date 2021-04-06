FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 80
WORKDIR /opt/app
ARG JAR_FILE=build/libs/cloud-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]