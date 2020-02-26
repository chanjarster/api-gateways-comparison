#!/bin/bash

docker build --force-rm --pull \
  -t chanjarster/api-gateway-comp-tomcat \
  .

docker push chanjarster/api-gateway-comp-tomcat
