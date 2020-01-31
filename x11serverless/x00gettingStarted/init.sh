#!/bin/bash

gradle init \
    --type java-application \
    --dsl=groovy \
    --project-name=serverless-basics \
    --package=serverless-basics \
    --test-framework=junit