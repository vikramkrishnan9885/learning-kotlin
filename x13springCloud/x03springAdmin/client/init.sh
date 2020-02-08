#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.kotlinlearning \
    -d name=admin-server-client \
    -o admin-server-client.zip
    
unzip admin-server-client.zip
