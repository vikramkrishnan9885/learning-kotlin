#!/bin/bash
mvn archetype:generate \
    -DgroupId=com.vk.activemqtesting \
    -DartifactId=kotlin-messaging-app \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
