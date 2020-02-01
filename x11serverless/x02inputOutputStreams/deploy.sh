#!/bin/bash

echo "Creating s3 Bucket ..."
aws s3 mb s3://vk-serverless-20200201 --profile personal

echo "Building the fat jar ..."
cd kotlin-app 
mvn clean package

echo "Move jar to s3 ..."
aws s3 cp target/greeter-1.0.0.jar s3://vk-serverless-20200201/greeter-1.0.0.jar  --profile personal
cd ..


# POLICY - ROLE - ATTACH

echo "Creating policy for lambda to enable logs ..."
aws iam create-policy \
    --policy-name lambda_iam_policy_test \
    --policy-document file://basic-lambda-policy.txt \
    --profile personal

echo "Creating Role for lambda ..."
aws iam create-role \
    --role-name lambda_iam_role_test \
    --assume-role-policy-document file://basic-lambda-iam-role.txt \
    --profile personal

echo "Attach roles to policies ..."
aws iam attach-role-policy \
    --role-name lambda_iam_role_test \
    --policy-arn arn:aws:iam::838565391653:policy/lambda_iam_policy_test \
    --profile personal

sleep 300

# LAMBDA
echo "Create a Lambda function providing the role and the S3 location ..."
aws lambda create-function \
    --function-name demo-lambda-with-cli \
    --runtime java8 \
    --role arn:aws:iam::838565391653:role/lambda_iam_role_test \
    --handler com.vk.serverless.MyLambdaHandler::handleRequest \
    --code S3Bucket=vk-serverless-20200201,S3Key=greeter-1.0.0.jar \
    --timeout 15 \
    --memory-size 512 \
    --profile personal


aws lambda invoke \
    --invocation-type RequestResponse \
    --function-name demo-lambda-with-cli \
    --log-type Tail \
    --payload '{"who":"Vikram"}' \
    --profile personal \
    outputfile.txt

cat outputfile.txt