#!/bin/bash

cd kotlin-messaging-app
mvn clean package
java -jar target/messaging-1.0.0.jar