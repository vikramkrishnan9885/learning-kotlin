#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.kotlinlearning \
    -d name=ribbon-server \
    -o ribbon-server.zip
    
unzip ribbon-server.zip
