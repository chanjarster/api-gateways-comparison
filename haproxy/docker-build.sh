#!/bin/bash

docker build --force-rm --squash --pull \
  -t chanjarster/api-gateway-comp-haproxy \
  .

docker push chanjarster/api-gateway-comp-haproxy
