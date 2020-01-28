#!/bin/bash

if [ $1 -eq 0 ]
then
  rm -rf .gitignore
  rm -rf .mvn
  rm -rf .gradle
  rm -rf .idea
  rm build.gradle
  rm build.gradle.kts
  rm HELP.md
  rm -rf gradle
  rm gradlew
  rm gradlew.bat
  rm settings.gradle
  rm settings.gradle.kts
  rm spring-getting-started.zip
  rm -rf src
  rm -rf build
fi

spring init --build=gradle \
  --java-version=1.8 \
  --boot-version=2.2.4.RELEASE \
  --language=kotlin \
  -g=com.vk.spring \
  -a=spring-getting-started \
  --package-name=com.vk.spring \
  -name=spring-getting-started \
  #--dependencies=web,security,data-jpa,actuator,devtools \

unzip spring-getting-started.zip

.gradlew clean
.gradlew build
