#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.kotlinlearning \
    -d name=ribbon-eureka-server \
    -o ribbon-eureka-server.zip
    
unzip ribbon-eureka-server.zip
