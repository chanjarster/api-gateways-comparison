package main

import (
	routing "github.com/qiangxue/fasthttp-routing"
	"github.com/valyala/fasthttp"
	"github.com/valyala/fasthttp/pprofhandler"
	"log"
	"runtime"
	"time"
)

func makeProxy(target string) routing.Handler {

	client := &fasthttp.Client{
		MaxConnsPerHost:     3000,
		MaxIdleConnDuration: 5 * time.Minute,
		MaxConnDuration:     5 * time.Second,
		ReadTimeout:         30 * time.Second,
		WriteTimeout:        30 * time.Second,
	}

	var handler fasthttp.RequestHandler = func(ctx *fasthttp.RequestCtx) {

		req := &ctx.Request
		resp := &ctx.Response

		req.Header.Del("Connection")
		req.SetHost(target)

		if err := client.Do(req, resp); err != nil {
			ctx.Logger().Printf("error when proxying the request: %s", err)
			resp.SetStatusCode(fasthttp.StatusBadGateway)
			return
		}

		resp.Header.Del("Connection")
	}

	return func(ctx *routing.Context) error {
		handler(ctx.RequestCtx)
		return nil
	}

}

func pprofHandler() routing.Handler {

	// 设置Profile相关参数
	runtime.SetBlockProfileRate(1)
	runtime.SetMutexProfileFraction(1)

	return func(ctx *routing.Context) error {
		pprofhandler.PprofHandler(ctx.RequestCtx)
		return nil
	}

}

func main() {
	log.Println("Start listening on :9090")

	router := routing.New()
	router.Any("/debug/pprof/*", pprofHandler())
	router.Any("/*", makeProxy("tomcat:8080"))

	log.Fatal(fasthttp.ListenAndServe(":9090", router.HandleRequest))
}
