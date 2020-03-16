#!/bin/bash
mvn archetype:generate \
    -DgroupId=com.vk.activemqjobs \
    -DartifactId=job-producer \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false

mvn archetype:generate \
    -DgroupId=com.vk.activemqjobs \
    -DartifactId=job-consumer \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
