#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web \
    -d packageName=com.journaler.api \
    -d name=journaler \
    -d type=gradle-project \
    -o journaler.zip
    
unzip journaler.zip
