#!/bin/bash

docker pull mysql/mysql-server:latest
docker run --name=mysql01 -d mysql/mysql-server:latest
#sleep 2m
echo "================================================"
docker logs mysql1 2>&1 | grep GENERATED
echo "================================================"
