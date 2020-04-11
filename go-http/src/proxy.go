package main

import (
	"log"
	"net/http"
	"net/http/httputil"
	_ "net/http/pprof"
	"net/url"
	"time"
)

func makeProxy(backend string) http.HandlerFunc {
	target, _ := url.Parse(backend)
	reverseProxy := httputil.NewSingleHostReverseProxy(target)

	cloneTransport := http.DefaultTransport.(*http.Transport).Clone()
	cloneTransport.MaxIdleConns = 3000
	cloneTransport.MaxConnsPerHost = 3000
	cloneTransport.IdleConnTimeout = 55 * time.Second
	cloneTransport.ResponseHeaderTimeout = 30 * time.Second
	reverseProxy.Transport = cloneTransport

	oldDirector := reverseProxy.Director
	newDirector := func(req *http.Request) {
		oldDirector(req)
		req.Host = target.Host
	}
	reverseProxy.Director = newDirector

	return func(w http.ResponseWriter, r *http.Request) {
		reverseProxy.ServeHTTP(w, r)
	}
}

func main() {
	http.HandleFunc("/", makeProxy("http://tomcat:8080"))
	log.Println("Start listening on :9090")
	log.Fatal(http.ListenAndServe(":9090", nil))
}
