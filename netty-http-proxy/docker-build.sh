#!/bin/bash

mvn clean package

docker build --force-rm --pull \
  -t chanjarster/api-gateway-comp-netty-http-proxy:latest \
  .

docker push chanjarster/api-gateway-comp-netty-http-proxy:latest
