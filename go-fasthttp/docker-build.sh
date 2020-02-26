#!/bin/bash

docker build --force-rm --squash --pull \
  -t chanjarster/api-gateway-comp-go-fasthttp \
  .

docker push chanjarster/api-gateway-comp-go-fasthttp
