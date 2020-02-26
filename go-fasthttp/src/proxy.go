package main

import (
	"github.com/valyala/fasthttp"
	"log"
	"time"
)

func makeProxy(target string) fasthttp.RequestHandler {

	client := &fasthttp.Client{
		ReadTimeout:         30 * time.Second,
		WriteTimeout:        30 * time.Second,
		MaxConnDuration:     5 * time.Second,
		MaxConnsPerHost:     1024,
		MaxIdleConnDuration: 5 * time.Minute,
	}

	var handler fasthttp.RequestHandler = func(ctx *fasthttp.RequestCtx) {

		req := &ctx.Request
		resp := &ctx.Response

		req.Header.Del("Connection")
		req.SetHost(target)

		if err := client.Do(req, resp); err != nil {
			ctx.Logger().Printf("error when proxying the request: %s", err)
			return
		}

		resp.Header.Del("Connection")
	}

	return handler
}

func main() {
	log.Println("Start listening on :9090")
	log.Fatal(fasthttp.ListenAndServe(":9090", makeProxy("tomcat:8080")))
}
