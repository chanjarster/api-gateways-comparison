FROM golang:1.13-alpine

WORKDIR /go/src/proxy
COPY ./src/* .

#RUN go get -d -v ./...
RUN go install -v ./...

CMD ["proxy"]
