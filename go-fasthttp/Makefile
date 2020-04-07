image="chanjarster/api-gateway-comp-go-fasthttp"

build:
	docker build --force-rm --pull \
	-t ${image} \
	-f Dockerfile \
	.

push: build
	docker push ${image}

.PHONY: build push