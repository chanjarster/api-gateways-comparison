#!/bin/bash

docker build --force-rm --pull \
  -t chanjarster/api-gateway-comp-nginx \
  .

docker push chanjarster/api-gateway-comp-nginx
