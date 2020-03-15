#!/bin/bash

mvn archetype:generate \
    -DgroupId=com.vk.activemqtopics \
    -DartifactId=selective-event-consumer \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false -X