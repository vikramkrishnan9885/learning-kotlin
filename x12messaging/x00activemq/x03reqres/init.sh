#!/bin/bash

mvn archetype:generate \
    -DgroupId=com.vk.activemq \
    -DartifactId=requester \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false -X

mvn archetype:generate \
    -DgroupId=com.vk.activemq \
    -DartifactId=responder \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false -X