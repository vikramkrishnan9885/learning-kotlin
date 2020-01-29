#!/bin/bash

curl https://start.spring.io/starter.zip \
    -d language=kotlin \
    -d dependencies=web,mustache,jpa,h2,devtools \
    -d packageName=com.example.blog \
    -d name=blog \
    -o blog.zip
    
unzip blog.zip
