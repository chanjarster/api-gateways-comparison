image="chanjarster/api-gateway-comp-go-fasthttp"
tmpcontainer="go-fasthttp-tmp"

build:
	docker build --force-rm --pull \
	-t ${image} \
	-f Dockerfile \
	.
	# copy binary out
	docker run --rm -it -d --name ${tmpcontainer} ${image} /bin/sh
	docker cp ${tmpcontainer}:/go-fasthttp .
	docker stop ${tmpcontainer}
	
push: build
	docker push ${image}

.PHONY: build push