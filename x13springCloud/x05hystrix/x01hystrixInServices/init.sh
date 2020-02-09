#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.kotlinlearning \
    -d name=hystrix-server-2 \
    -o hystrix-server-2.zip
    
unzip hystrix-server-2.zip
