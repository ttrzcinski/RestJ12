FROM openjdk:14-alpine
# FROM openjdk:16-slim-buster
# FROM adoptopenjdk/openjdk14
# FROM openjdk:11-alpine
# COPY ./mvnw.sh mvnw.sh
# RUN /bin/sh -c "mvnw.sh clean install"
# apk is alpine only
RUN apk add --no-cache maven
# RUN apt-get update
# RUN apt-get install -y maven 
COPY target/RestJ11-*.jar RestJ11.jar
EXPOSE 8080
# CMD ["java", "--version"]
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "RestJ11.jar"]
