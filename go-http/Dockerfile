FROM golang:1.13-alpine

RUN apk add git
RUN mkdir /go/src/proxy
WORKDIR /go/src/proxy
COPY ./src/* .

#RUN go get -d -v ./...
RUN go build -v ./...

FROM alpine:latest

COPY --from=0 /go/src/proxy .

EXPOSE 9090
CMD ["./proxy"]