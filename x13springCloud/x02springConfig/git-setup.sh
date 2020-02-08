#!/bin/bash

mkdir -p git/cloud
cd git/cloud

git init

echo '''
limits-service.minimum=1
limits-service.maximum=11
''' >>  limits-service.properties


git add .

git commit -am "Initial commit"
