#!/bin/bash

cd git/cloud

# Properties file name should be {spring.application.name}-{environment}.properties

echo "test.greeting=Hi developer!" > test-dev.properties

echo "test.msg=How is your coding going?" > test2-dev.properties

echo "test.greeting=Hi there!" > test-prod.properties

echo "test.msg=How are you doing?" > test2-prod.properties

# At the specified location we need to add property files and commit them to Git before the clients can access them.
git add .
git commit -am "More files added"