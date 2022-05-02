FROM openjdk:19-ea-16-slim

RUN apk add --no-cache maven
COPY target/RestJ11-*.jar RestJ11.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.managment.jmx.remote" "-Xmx128m","-jar","RestJ11.jar"]
