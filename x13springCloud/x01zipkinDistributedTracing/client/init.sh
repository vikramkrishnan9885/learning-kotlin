#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.kotlinlearning \
    -d name=eureka-client \
    -o eureka-client.zip
    
unzip eureka-client.zip
