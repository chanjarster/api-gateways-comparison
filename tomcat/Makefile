image="chanjarster/api-gateway-comp-tomcat"

build:
	docker build --force-rm --pull \
	-t ${image} \
	-f Dockerfile \
	.

push: build
	docker push ${image}

.PHONY: build push