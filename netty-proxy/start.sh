#!/bin/bash

docker run \
  -p 9090:80 \
  --rm \
  -v $(pwd)/tomcat.conf:/etc/nginx/conf.d/default.conf:ro \
  --add-host tomcat:192.168.2.1 \
  nginx

