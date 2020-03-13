#!/bin/bash
mvn archetype:generate \
    -DgroupId=com.vk.activemqtopics \
    -DartifactId=event-publisher \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false -X

mvn archetype:generate \
    -DgroupId=com.vk.activemqtopics \
    -DartifactId=event-subscriber \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false -X