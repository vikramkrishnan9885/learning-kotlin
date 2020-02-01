#!/bin/bash

echo "Deleting the lambda ..."
aws lambda delete-function \
    --function-name demo-lambda-with-cli \
    --profile personal

echo "Detaching role from policy ..."
aws iam detach-role-policy \
    --role-name lambda_iam_role_test \
    --policy-arn arn:aws:iam::838565391653:policy/lambda_iam_policy_test \
    --profile personal

echo "Delete role ..."
aws iam delete-role \
    --role-name lambda_iam_role_test \
    --profile personal

echo "Delete policy ..."
aws iam delete-policy \
    --policy-arn arn:aws:iam::838565391653:policy/lambda_iam_policy_test \
    --profile personal

echo "Deleting s3 Bucket ..."
aws s3 rb s3://vk-serverless-20200201 --force --profile personal