#!/bin/bash

terraform destroy -auto-approve 

rm -rf .terraform
rm .terraform.tfstate.lock.info
rm terraform.tfstate
rm terraform.tfstate.backup