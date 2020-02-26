#!/bin/bash

mvn clean package

docker build --force-rm --pull \
  -t chanjarster/api-gateway-comp-netty-proxy:latest \
  .

docker push chanjarster/api-gateway-comp-netty-proxy:latest
