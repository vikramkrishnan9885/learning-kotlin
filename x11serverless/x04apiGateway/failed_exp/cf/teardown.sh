#!/bin/bash

aws cloudformation delete-stack \
    --stack-name myteststack \
    --region us-east-1 \
    --profile personal