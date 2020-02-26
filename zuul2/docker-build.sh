#!/bin/bash

mvn clean package

docker build --force-rm --pull \
  -t chanjarster/api-gateway-comp-zuul2:latest \
  .

docker push chanjarster/api-gateway-comp-zuul2:latest
