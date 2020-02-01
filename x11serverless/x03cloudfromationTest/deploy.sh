#!/bin/bash


# THIS BIT REMAINS THE SAME

echo "Creating s3 Bucket ..."
aws s3 mb s3://vk-serverless-20200201 --profile personal

echo "Building the fat jar ..."
cd kotlin-app 
mvn clean package

echo "Move jar to s3 ..."
aws s3 cp target/greeter-1.0.0.jar s3://vk-serverless-20200201/greeter-1.0.0.jar  --profile personal
cd ..


# THIS BIT CHANGES

# https://stackoverflow.com/questions/49316884/insufficientcapabilitiesexception-capability-named-iam-when-creating-a-stack-w
# https://docs.aws.amazon.com/AWSCloudFormation/latest/APIReference/API_CreateStack.html
echo "Create stack ..."
aws cloudformation create-stack \
    --stack-name myteststack \
    --template-body file://cf-template.yml \
    --region us-east-1 \
    --capabilities CAPABILITY_NAMED_IAM \
    --profile personal

echo "Stack description ..."
aws cloudformation describe-stacks \
    --stack-name myteststack \
    --region us-east-1 \
    --profile personal > stack-description.txt

cat stack-description.txt

aws lambda invoke \
    --invocation-type RequestResponse \
    --function-name first-lambda-with-cloud-formation \
    --log-type Tail \
    --payload '{"name":"Vikram"}' \
    --profile personal \
    outputfile.txt

cat outputfile.txt