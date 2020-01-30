#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,actuator \
    -d packageName=com.vk.security \
    -d name=shiro \
    -o shiro.zip
    
unzip shiro.zip
