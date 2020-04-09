FROM golang:1.13-alpine

RUN apk add git
RUN mkdir /proxy
WORKDIR /proxy
COPY ./. .

RUN go get -v
RUN go build -mod=vendor -v

FROM alpine:latest

COPY --from=0 /proxy/go-fasthttp .

EXPOSE 9090
CMD ["./go-fasthttp"]