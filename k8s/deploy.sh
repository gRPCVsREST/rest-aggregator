#!/bin/bash

if [ -z $GCP_PROJECT ]; then echo "GCP_PROJECT is not set"; exit -1; fi
if [ -z $CONTENT_TYPE_A ]; then echo "CONTENT_TYPE_A is not set"; exit -1; fi
if [ -z $CONTENT_TYPE_B ]; then echo "CONTENT_TYPE_B is not set"; exit -1; fi

$(dirname $0)/rest-aggregator.yaml.sh | kubectl apply -f -