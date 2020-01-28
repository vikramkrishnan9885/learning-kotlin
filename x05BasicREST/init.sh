#!/bin/sh

curl https://start.spring.io/starter.zip \
        -d language=kotlin \
        -d dependencies=web \
        -d packageName=com.vk \
        -d name=spring-kotlin \
        -d type=gradle-project \
        -o spring-kotlin.zip

unzip spring-kotlin.zip
