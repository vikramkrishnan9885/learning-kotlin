#!/bin/bash

mvn archetype:generate \
    -DgroupId=com.vk.ddbtest \
    -DartifactId=basics \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false -X