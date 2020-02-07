#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d bootVersion=2.2.1.RELEASE \
    -d language=kotlin \
    -d dependencies=web,actuator,devtools \
    -d packageName=com.vk.kotlinlearning \
    -d name=zipkin-server \
    -o zipkin-server.zip
    
unzip zipkin-server.zip
