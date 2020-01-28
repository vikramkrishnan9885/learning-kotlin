#!/bin/bash

if [ $1 -eq 0 ]
then
  rm -rf .gitignore
  rm -rf .mvn
  rm HELP.md
  rm build.gradle
  rm -rf gradle
  rm gradlew
  rm gradlew.bat
  rm settings.gradle
  rm spring-getting-started.zip
  rm -rf src
fi

spring init --build=gradle \
  --java-version=1.8 \
  --boot-version=2.0.0.RELEASE \
  --language=kotlin \
  --dependencies=web,security,data-jpa,actuator,devtools \
  -g=com.vk.spring \
  -a=spring-getting-started \
  --package-name=com.vk.spring \
  -name=spring-getting-started

unzip spring-getting-started.zip
