#!/bin/bash

aws cloudformation create-stack \
    --stack-name myteststack \
    --template-body file://cf-template.yml \
    --region us-east-1 \
    --profile personal \
    --capabilities CAPABILITY_NAMED_IAM 

sleep 300

aws cloudformation describe-stacks \
    --stack-name myteststack \
    --region us-east-1 \
    --profile personal