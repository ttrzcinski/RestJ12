FROM openjdk:14-alpine
# COPY ./mvnw.sh mvnw.sh
# RUN /bin/sh -c "mvnw.sh clean install"
RUN apk add --no-cache maven
COPY target/RestJ11-*.jar RestJ11.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "RestJ11.jar"]
