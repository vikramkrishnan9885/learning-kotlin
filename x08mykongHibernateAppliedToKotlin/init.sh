#!/bin/bash

gradle init \
    --dsl=kotlin \
    --type=java-application \
    --project-name=hibernate-examples \
    --package=hibernate-examples \
    --test-framework=junit