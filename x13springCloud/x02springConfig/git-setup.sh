#!/bin/bash

mkdir -p git/cloud
cd git/cloud

git init

#
# SUPER IMP
# spring.application.name - Client should have this in its application.properties
# Properties file name should be {spring.application.name}-{environment}.properties
#
echo '''
limits-service.minimum=1
limits-service.maximum=11
''' >>  limits-service.properties


git add .

git commit -am "Initial commit"
