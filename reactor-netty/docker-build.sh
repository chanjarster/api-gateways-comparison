#!/bin/bash

mvn clean package

docker build --force-rm --squash --pull \
  -t chanjarster/api-gateway-comp-reactor-netty:latest \
  .

docker push chanjarster/api-gateway-comp-reactor-netty:latest
