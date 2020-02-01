#!/bin/bash

aws cloudformation delete-stack \
    --stack-name myteststack \
    --region us-east-1 \
    --profile personal

echo "Deleting s3 Bucket ..."
aws s3 rb s3://vk-serverless-20200201 --force --profile personal