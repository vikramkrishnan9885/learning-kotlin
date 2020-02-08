#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.kotlinlearning \
    -d name=config-server-client \
    -o config-server-client.zip
    
unzip config-server-client.zip
