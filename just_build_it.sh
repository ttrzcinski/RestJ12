#!/bin/bash
# Any error is a show stopper
set -e
# Variables
DOCKER_NAME="microlambda0.2"
echo "Will now build that Lambda."
sh gradlew assemble
# sh gradlew clean build test check
echo "Will now build it for AWS."
sh gradlew shadowJar
echo "Will now extract it for Docker."
unzip -p build/distributions/complete-shadow-0.2.zip complete-shadow-0.2/lib/complete-0.2-all.jar > target/RestJ11-0.2.jar
# echo "Will now copy it for docker"
# cp build/distributions/complete-0.2.zip target/RestJ11-0.2.jar
echo "Will now dockerize it."
# sh gradlew dockerfile
docker build -t "$DOCKER_NAME" .
# sh gradlew dockerBuild
docker run --rm -p 8080:8080 "$DOCKER_NAME"
echo "DONE"
