FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 80
WORKDIR /opt/app
ARG JAR_FILE=build/libs/machine-to-machine-client-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} machine-to-machine-client.jar
ENTRYPOINT ["java","-jar","machine-to-machine-client.jar"]