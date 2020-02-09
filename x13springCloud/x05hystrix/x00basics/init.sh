#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.kotlinlearning \
    -d name=hystrix-server \
    -o hystrix-server.zip
    
unzip hystrix-server.zip
