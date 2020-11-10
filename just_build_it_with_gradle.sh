#!/bin/bash
echo "Will now build that Lambda."
sh gradlew clean build test check
echo "DONE"